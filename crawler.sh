#!/bin/bash

cd /Users/zzy/Documents/eclipse/WPT/bin
echo "ads"
java -cp .:/Users/zzy/Downloads/selenium-server-standalone-2.44.0.jar wpt/AdsCrawler
echo "html"
java -cp .:/Users/zzy/Downloads/selenium-server-standalone-2.44.0.jar wpt/HTMLCrawler
echo "icon"
java -cp .:/Users/zzy/Downloads/selenium-server-standalone-2.44.0.jar wpt/IconCrawler 
echo "css"
java -cp .:/Users/zzy/Downloads/selenium-server-standalone-2.44.0.jar wpt/CSSCrawler
echo "js"
java -cp .:/Users/zzy/Downloads/selenium-server-standalone-2.44.0.jar wpt/ScriptCrawler
echo "flash"
java -cp .:/Users/zzy/Downloads/selenium-server-standalone-2.44.0.jar wpt/FlashCrawler

today=`date +%Y%m%d`
msg="update_"$today""
echo $msg
cd /Users/zzy/Documents/script
git ls-files --deleted -z | git update-index --assume-unchanged -z --stdin
git add .
git commit -m $msg
git push origin master

