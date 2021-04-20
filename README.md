### 使用方式:
1.加载到idea

2.add 到maven工程

3.运行com\unidbg\api\ApiApplication.java即可启动服务

### 修改配置和代码
通过application.properties自行修改服务的地址和端口

调用示例文件参考NativeUtils.java
### 打jar包
使用maven的package即可，之后会发现生成一个target目录其中里面就有jar包了。
### 使用jar包
```
cd target
jar -xf unidbg-server-0.0.1-SNAPSHOT.jar
java -cp BOOT-INF/classes:BOOT-INF/lib/* com.unidbg.api.ApiApplication.java
java -jar unidbg-server-0.0.1-SNAPSHOT.jar
```
### Python调用示例
```
import requests
url = 'http://127.0.0.1:9091/'
param = {'param': '{"tag":"getContentJNI"}'}
req=requests.get(url,param=data)
print(req.text)
```
### 感谢
[https://github.com/zhkl0228/unidbg](https://github.com/zhkl0228/unidbg "https://github.com/zhkl0228/unidbg")

