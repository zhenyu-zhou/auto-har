#!/bin/bash

url="http://skylynx.cs.duke.edu/web2/alvin/"
path="/home/zzy/site/web2/alvin/index_files/"

# rm -r ~/try/del/bg/
# cd ~/try/del/
# mkdir bg
# cd bg
# wget -r $url >/dev/null 2>&1

cd /Users/zzy/Downloads/phantomjs-2.0.0-macosx/bin
rm bg.har
./phantomjs ../examples/netsniff.js.bak $url > bg.har

ret_code=0

while [ $ret_code -eq 0 ]; do
	# cd ~/try/del/
	ssh zzy@skylynx.cs.duke.edu -t java DelInTurn $path
	ret_code=$?
	# mkdir delta
	# cd delta
	# wget -r $url >/dev/null 2>&1
	# diff -rq ~/try/del/bg/ ~/try/del/delta/
	# rm -r ~/try/del/delta/
	cd /Users/zzy/Downloads/phantomjs-2.0.0-macosx/bin
	rm out.har
	./phantomjs ../examples/netsniff.js.bak $url > out.har
	cd /Users/zzy/Documents/eclipse/WPT/bin
	java -cp .:/Users/zzy/Downloads/java-json.jar wpt.HARReaderPhantomjs
done;
 
