JFLAGS = -g
JC = javac
JAVA = java
.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		JDBCDemo.java

default: classes

classes: $(CLASSES:.java=.class)
	@echo "Type: make run to execute."

clean:
	$(RM) *.class

run: classes
	@$(JAVA) $(basename $(CLASSES))
