

:: time ~~~~~
set a=%date:~0,10%_
set b=%TIME:~0,2%
if %TIME:~0,2% leq 9 (set b=0%TIME:~1,1%)else set b=%TIME:~0,2%
set c=%TIME:~3,2%
set ti=%a%%b%%c%

set v=latest
set v=v3

docker build . -t registry.cn-hangzhou.aliyuncs.com/img_pub/dubbo-provider:%v%
docker push registry.cn-hangzhou.aliyuncs.com/img_pub/dubbo-provider:%v%







