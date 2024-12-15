#!/bin/bash

# Usage: ./generate_rdf.sh <input_rml> <output_rdf>
RML_MAPPER="./rmlmapper-6.5.1-r371-all.jar" # Change this to the path of your rmlmapper jar file

if [ $# -ne 2 ]; then
    echo "Usage: $0 <input_rml_file> <output_rdf_file>"
    exit 1
fi

java -jar $RML_MAPPER -m $1 -o $2 -s turtle
