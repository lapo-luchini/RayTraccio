all: RayTraccio.class

%.class: %.java
	javac -sourcepath . $^

#SDL.java: SDL.jj
#	cmd /C javacc $^

jar:
	jar cvfm RayTraccio.jar MANIFEST.MF *.class lacrypto/*.class

doc:
	javadoc -verbose -private -d docs/ -author -version -windowtitle RayTraccio -doctitle RayTraccio -use *.java lacrypto/*.java -bottom '<a href="http://www.lapo.it/RayTraccio.html">RayTraccio</a> by <a href="mailto:lapo@lapo.it?subject=Feedback RayTraccio" title="Feedback RayTraccio">Lapo Luchini</a>'

clean:
	rm -f *.class
	rm -f lacrypto/*.class
	rm -f *.jar
