class TexturePlasma extends Texture {
  private Texture c[];
  private short v[][];
TexturePlasma(Texture a, Texture d) {
	c = new Texture[2];
	c[0] = a;
	c[1] = d;
	v = new short[64][64];
	for (int i = 0; i < 64; i++)
		for (int l = 0; l < 64; l++)
			v[i][l] = -1;
	v[0][0] = 8192;
	fill(0, 0, (byte) 32);
	int min = 10000, max = 0;
	for (int i = 0; i < 64; i++)
		for (int l = 0; l < 64; l++) {
			if (v[i][l] < min)
				min = v[i][l];
			if (v[i][l] > max)
				max = v[i][l];
		}
	for (int i = 0; i < 64; i++)
		for (int l = 0; l < 64; l++)
			v[i][l] = (short) ((v[i][l] - min) * (1024.0 / (max - min)));
}
public Color color(Vector p) {
	double r = v[conv(p.x)][conv(p.y)] / 1024.0;
	if (r < 0.0)
		return (Color.RED);
	return (c[0].color(p).mul(r).addU(c[1].color(p).mul(1.0 - r)));
}
private byte conv(double a) {
	int c = (int) ((a - Math.floor(a)) * 64);
	if (c < 0) {
		System.out.println(c);
		c += 64;
	}
	return ((byte) c);
}
private void fill(int x, int y, byte d) {
	int x1d = x + d, x2d = (x + 2 * d) % 64, y1d = y + d, y2d = (y + 2 * d) % 64;
	if (v[x1d][y] == -1)
		v[x1d][y] = (short) rand_intervalue((v[x][y] + v[x2d][y]) / 2, d);
	if (v[x][y1d] == -1)
		v[x][y1d] = (short) rand_intervalue((v[x][y] + v[x][y2d]) / 2, d);
	if (v[x1d][y1d] == -1)
		v[x1d][y1d] = (short) rand_intervalue((v[x][y] + v[x2d][y] + v[x][y2d] + v[x2d][y2d]) / 4, d);
	if (v[x2d][y1d] == -1)
		v[x2d][y1d] = (short) rand_intervalue((v[x2d][y] + v[x2d][y2d]) / 2, d);
	if (v[x1d][y2d] == -1)
		v[x1d][y2d] = (short) rand_intervalue((v[x][y2d] + v[x2d][y2d]) / 2, d);
	if (d > 1) {
		fill(x, y, (byte) (d / 2));
		fill(x + d, y, (byte) (d / 2));
		fill(x, y + d, (byte) (d / 2));
		fill(x + d, y + d, (byte) (d / 2));
	}
}
private int rand_intervalue(int a, byte dist) {
	int u = a + (int) Math.floor((128 * dist) * (Math.random() - 0.5));
	if (u < 0)
		u = 0;
	else
		if (u > 8192)
			u = 8192;
	return (u);
}
public double reflect(Vector p) {
	double r = v[conv(p.x)][conv(p.y)] / 1024.0;
	if (r < 0.0)
		return (0.0);
	return (c[0].reflect(p) * r + c[1].reflect(p) * (1.0 - r));
}
public String toString() {
	return ("TexturePlasma[" + c[0] + "," + c[1] + "]");
}
}
