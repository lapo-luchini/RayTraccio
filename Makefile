all: RayTraccio.class

%.class: %.java
	javac -sourcepath . $^

jar:
	jar cvfm RayTraccio.jar MANIFEST.MF *.class lacrypto/*.class

clean:
	rm -f *.class
	rm -f lacrypto/*.class
	rm -f *.jar
