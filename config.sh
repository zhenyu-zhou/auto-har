DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

remote="$1"
laten="$2"
bd="$3"
remote2="$4"
laten2="$5"
bd2="$6"

#sh server.sh $remote $laten $bd $remote2 $laten2 $bd2

url_tra="$7"
url_split="$8"

cd /Users/zzy/Documents/eclipse/WPT/bin
java -cp .:/Users/zzy/Downloads/selenium-server-standalone-2.44.0.jar wpt/WebPageTest $url_tra $url_split

har_dir=/Users/zzy/Documents/eclipse/WPT/bin/har/
num=0
for file_a in ${har_dir}/*; do
    temp_file=`basename $file_a`
    echo $temp_file
    if [ $num -eq 0 ]; then
      f1=$temp_file
    fi
    if [ $num -eq 1 ]; then
      f2=$temp_file
    fi
    num=$[$num+1]
done

echo f1:${f1}
echo f2:${f2}

cd $har_dir
#f1="$9"
#f2="${10}"
out="${9}" #"${11}"
sh ${DIR}/auto_har.sh ${har_dir}${f1} ${har_dir}${f2} $out

sleep 3s

cd $har_dir
for file_a in ${har_dir}/*; do
    temp_file=`basename $file_a`
    mv ${temp_file} ../${temp_file}
done


