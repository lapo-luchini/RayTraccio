all: SDL.class RayTraccio.class

JAVAC = jikes

%.class: %.java
	$(JAVAC) $^

SDL.java: SDL.jj
	javacc.cmd $^

jar:
	jar cfm RayTraccio.jar MANIFEST.MF gpl.txt gpl-ita.txt *.class lacrypto/*.class

pkg:
	tar cjf RayTraccio.tar.bz2 --exclude=SDL*.java --exclude=ASCII_CharStream.java --exclude=Token*.java --exclude=ParseException.java Makefile MANIFEST.MF gpl.txt gpl-ita.txt SDL.jj RayTraccio.html default.sdl *.java lacrypto/*.java

doc:
	javadoc -verbose -private -d docs -author -version -windowtitle RayTraccio -doctitle RayTraccio -use *.java lacrypto/*.java -bottom '<a href="http://www.lapo.it/RayTraccio.html">RayTraccio</a> by <a href="mailto:lapo@lapo.it?subject=Feedback RayTraccio" title="Feedback RayTraccio">Lapo Luchini</a>'

clean:
	rm -f *.class
	rm -f lacrypto/*.class
	rm -f RayTraccio.jar
	rm -f RayTracio.tar.bz2
