all: RayTraccio.class

%.class: %.java
	javac $^

jar:
	jar cvfm RayTraccio.jar MANIFEST.MF *.class
	rm *.class

clean:
	rm *.class
	rm *.jar
