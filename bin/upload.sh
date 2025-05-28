#!/bin/zsh

clear

PRO_ROOT="/Users/chenyue/appData/gitRepo/workspace4/cystrixblog/"
cd "$PRO_ROOT" || { echo "切换目录失败: $PRO_ROOT"; exit 1; }

mvn clean package -DskipTests

upload() {
  JAR_FILE="./target/cystrixblog-1.1.2.jar"
    if [[ ! -r "$JAR_FILE" ]]; then
      echo "错误：文件 $JAR_FILE 不存在或无法读取，上传失败"
      exit 1
    fi

    scp "$JAR_FILE" root@111.229.171.30:/root/app/cbbd/
    if [[ $? -eq 0 ]]; then
      echo "上传成功!"
    else
      echo "上传失败，请检查网络连接或服务器状态"
      exit 1
    fi
}

upload

exit 0
