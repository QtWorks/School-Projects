#!/bin/sh
javac -cp ./ejml-0.23.jar:./slf4j-api-1.7.21.jar:./stanford-corenlp-3.6.0.jar:./stanford-corenlp-3.6.0-javadoc.jar:./stanford-corenlp-3.6.0-models.jar:./stanford-corenlp-3.6.0-sources.jar:. ./*.java
java -cp ./ejml-0.23.jar:./slf4j-api-1.7.21.jar:./stanford-corenlp-3.6.0.jar:./stanford-corenlp-3.6.0-javadoc.jar:./stanford-corenlp-3.6.0-models.jar:./stanford-corenlp-3.6.0-sources.jar:. Main ./listfile.txt ./response/
