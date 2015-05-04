#!/bin/bash

url="http://skylynx.cs.duke.edu/amazon/"
path="/home/zzy/site/amazon/"

cd /Users/zzy/Downloads/phantomjs-2.0.0-macosx/bin
rm bg.har
./phantomjs ../examples/netsniff.js.bak $url > bg.har

ret_code=0

while [ $ret_code -eq 0 ]; do
	ssh zzy@skylynx.cs.duke.edu -t java ChangeURLInTurn $path
	ret_code=$?
	cd /Users/zzy/Downloads/phantomjs-2.0.0-macosx/bin
	rm out.har
	./phantomjs ../examples/netsniff.js.bak $url > out.har
	cd /Users/zzy/Documents/eclipse/WPT/bin
	java -cp .:/Users/zzy/Downloads/java-json.jar wpt.HARReaderPhantomjs
done;
 
