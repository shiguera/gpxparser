#!/bin/bash
 
java -cp ${install-dir}/${artifactId}/lib -jar ${install-dir}/${artifactId}/${build.finalName}.jar $*
