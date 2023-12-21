package com.cystrix.blog.util;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description 关于字符串操作的处理写在着
 */
public class StringUtils {

    public static int countWords(String origin) {
        int count = 0;
        if (origin ==  null) {
            return count;
        }
        String[] words = origin.trim().split("\\s+");
        return words.length;
    }

    public static void main(String[] args) {
        String text = """
                >  本文档记录所有使用过的操作系统shell命令
                                
                ### Linux\s
                                
                ```bash
                hostnamectl set-hostname xh01      # 永久修改主机名称，重启生效
                hostname xh01                      # 临时修改，重启恢复
                uname -a                           # 查看系统信息
                sync                               # 缓存强制写回
                shutdown -h now                    # 立即关机
                shutdown -s -t 3600                # 3600秒后关机
                shutdown -a                        # 取消关机
                shutdown -r now                    # 立刻重启
                halt                               # 和shutdown -h now 效果一样
                ip addr:ens33 inet或者ifconfig      # vmware虚拟机查看ip
                                
                                
                                
                ./nginx-1.19.1.tar.get  TARGET_DIC/  # 复制文件到指定文件夹
                rm -rf /etc                           # 强制删除文件或文件夹
                echo '<h1>Hello, Docker!</h1>' > /usr/share/nginx/html/index.html  # 覆盖文档内容
                tar -zxvf ./backup.tar.gz             # 解压文件到当前文件夹
                tar czvf backup.tar.gz /etc           # 文件夹压缩成back.tar.gz文件
                ps -ef | grep nginx                   # 查找含有指定字符串的进程信息                \s
                ip addr                               # 查看ip信息
                cat >> filename                       # 创建文件并写入内容 ctrl+d 保存文件
                cat filename                          # 查看文件内容
                cp -r hadoop/. /etc/hadoop/           # 复制文件夹内所有文件到另一个文件夹
                shift+:/xxx                           # vim高亮指定内容，按n下一个 取消高亮:noh
                ll -a                                 # 查看所有文件包括隐藏文件
                # 防火墙操作
                systemctl start firewalld             # 启动防火墙
                systemctl status firewalld            # 查看防火墙信息
                systemctl stop firewalld              # 关闭防火墙
                systemctl enable firewalld            # 开启自启动
                systemctl disable firewalld           # 关闭自启动
                systemctl is-enabled firewalld        # 查看是否自启动
                systemctl list-unit-files | grep enabled # 查看已启动的服务
                sudo ufw status                       # ubuntu查看防火墙状态
                # 端口操作
                firewall-cmd --add-port=8080/tcp --permanent    # 永久开启tcp8080端口
                firewall-cmd --reload                           # 端口规则刷新
                firewall-cmd --remove-port=8080/tcp --permanent # 永久关闭tcp8080端口,需--reload
                firewall-cmd --list-port                        # 查看开放的端口
                #\s
                yum -y install net-tools                           # 安装netstat
                yum install -y unzip zip                           # 安装zip包解压工具
                netstat -anp | grep 22                             # 查看22号端口是否开启监控
                cat /etc/passwd                                    # 查看所有用户
                sudo adduser username                              # 添加用户
                sudo passwd username                               # 设置用户密码
                sudo visudo                                        # 配置管理员权限
                username ALL=(ALL:ALL)ALL                          # 配置管理员权限
                userdel USERNAME -r                                # 删除用户以及用户文件夹
                yum list installed                                 # 查看所有已安装的包
                yum list installed docker*                         # 搜索与docker相关的软件包
                make test                                          # 测试makefile文件是否正确
                make install                                       # make安装
                tail -f FILENAME -n 10                             # 动态查看文件末尾内容
                head -f FILENAME -n 10                             # 动态查看文件开头内容
                vim ~/.bashrc                                      # ubuntu配置环境变量
                source ~/.bashrc                                   # 刷新配置文件
                sudo apt-get install xxx                           # ubuntu安装软件包
                sudo apt-get remove --purge xx                     # 彻底删除包软件
                sudo apt update                                    # 更新软件列表
                sudo apt upgrade                                   # 升级软件包
                sudo apt install tree                              # 树形打印
                tree -R -L num ./path                              # 树形打印目录树
                sudo passwd root                                   # 设置root用户
                free -m                                            # 查看内存使用情况
                df -h                                              # 查看磁盘使用情况
                sudo apt-get install openssh-client                # 安装ssh服务
                sudo apt-get install openssh-server                # 安装ssh服务 服务端
                sudo /etc/init.d/ssh start                         # 启动ssh服务
                service ssh restart                                # 重启ssh服务
                nohup ./bin/zookeeper-server-start.sh config/zookeeper.properties > zk.log 2>&1 &# 挂起程序
                > redis.log                                        # 清空文件内容
                lsof -i:8080                                       # 查看8080端口占用情况
                df -hl                                             # 查看磁盘使用情况
                ll                                                 # 查看文件大小
                > FILENAME                                         # 清空文件内容
                du -sh FILENAME                                    # 查看文件大小
                netstat  -tunlp | grep PORT                        # 查看端口占用情况
                go version                                         # 查看golang版本
                docker version                                     # 查看docker版本
                history                                            # 查看使用过的命令\s
                !number                                            # number为 history中查询的命令编号
                go env -w GOPROXY=https://goproxy.cn,direct        # 设置go国内镜像源
                service xxx start                                  # 启动xxx服务
                service xxx stop  								   # 关闭xxx服务
                service xxx restart  					           # 重启xxx服务
                service xxx status                                 # cxxx服务       \s
                ```
                                
                ### Windows
                                
                ```bash
                shutdown -s -t 0                                    # 立刻关机
                shutdown -r -t 0                                    # 立刻重启
                taskkill /f /pid 16900                              # 杀死进程号为16900的进程
                tasklist|findstr 3112 							    # 查看进程号为3112的进程信息
                netstat -aon | findstr port                         # 查看端口占用情况
                ipconfig /displaydns                                # 查看dns缓存
                ipconfig /flushdns                                  # 刷新dns缓存
                ipcofig  /renew                                     # 重新请求DHCP分配IP
                c:\\Windows\\System32\\drivers\\etc\\hosts               # 本地hosts文件地址
                任务管理器-->启动                                      # 软件自启动管理  \s
                设置-->时间和语言-->管理语言设置-->区域-->管理             # 更改本机字符集          \s
                chcp                                                # 查看本机字符集 936GB2312 65001UTF-8
                ```
                                
                > VMWare Workstation Pro 16 激活秘钥
                                
                ZF3R0-FHED2-M80TY-8QYGC-NPKYF
                                
                > Windows11 不重启生效环境变量
                                
                ```shell
                # win+R 打开 cmd
                set PATH=c:
                # 关闭cmd，win+r重新打开cmd
                echo %PATH%
                ```
                                
                > :1564\s
                                
                vim 使用:冒号+数字调转到指定行
                                
                #### PowerShell 7.4.0
                                
                ##### Windows PowerShell 5.1 与 PowerShell 7.x 之间的差异
                                
                > Windows PowerShell 5.1 是在 .NET Framework v4.5 基础上构建的。 随着 PowerShell 6.0 的发布，PowerShell 成为基于 .NET Core 2.0 构建的开源项目。 从 .NET Framework 转换到 .Net Core 使 PowerShell 成为可跨平台的解决方案。 PowerShell 在 Windows、macOS 和 Linux 上运行。
                                
                 ![image-20231220104051947](E:\\YueStudio\\本科\\computer science\\typroa_image\\image-20231220104051947.png)\s
                                
                ### 其他
                                
                #### 1. ps -ef | grep 说明
                                
                > ps命令将某个进程显示出来
                > grep命令是查找
                > 中间的 | 是管道命令 指ps命令和grep命令同时执行
                > ps是linux下最常用的也是非常强大的进行查看命令
                > grep是查找命令,一种文本搜索工具 全称(Global Regular Expression Print)
                > -ef 查找出来的字段列表 ppid为父进程
                > 查询结果字段含义: UID PID PPID C STIME TTY TIME CMD
                                
                #### 2. make命令说明
                                
                > 在 Linux环境下使用 GNU 的 make工具能够比较容易的构建一个属于你自己的工程，整个工程的编译只需要一个命令就可以完成编译、连接以至于最后的执行。不过这需要我们投入一些时间去完成一个或者多个称之为 Makefile 文件的编写。此文件正是 make 正常工作的基础。make 是一个命令工具，它解释 Makefile 中的指令（应该说是规则）。在 Makefile文件中描述了整个工程所有文件的编译顺序、编译规则。
                >
                > `make` 执行在 `Makefile` 文件中定义的一系列任务将软件源代码编译成可执行文件。
                >
                > `make test`
                >
                > `make install ` 将可执行文件、第三方依赖包和文档复制到正确的路径。
                                
                ```BASH
                yum -y install ncurses-devel openssl-devel elfutils-libelf-devel bc rsync rpmdevtools python3 gcc gtk2-devel
                                
                yum install dnf-plugins-core -y\s
                dnf config-manager --set-enabled powertools
                                
                yum install ninja-build -y\s
                yum install gtk2-devel -y\s
                yum install liburing-devel.x86_64  liburing.x86_64  -y
                yum install libaio-devel.x86_64 -y
                ```
                                
                ##### 在windows下切换node版本
                                
                ```shell
                npm install -g npm                           # 更新包
                npm install -g node@16.20.0
                npm view node versions                       # 查看包所有的版本
                ```
                                
                #### 在centos7上安装curl
                                
                [Centos7环境下安装curl - 勿埋我心 (skyqian.com)](https://www.skyqian.com/archives/curl.html)
                                
                ##### npm切换源
                                
                ```bash
                # 下载 nrm源管理工具
                npm install nrm -g
                # 查看当前所有的源信息
                nrm ls\s
                # 切换源
                nrm use taobao\s
                # 查看当前源
                npm config get registry\s
                ```
                                
                ##### 切换node&npm版本
                                
                ```bash
                #安装全局n工具包 (win下无法安装n)
                npm install -g n
                # 查看node所有版本
                n list
                # 切换版本
                n node 10.16.2
                # win下切换node版本使nvm\s
                # nvm下载地址 https://github.com/coreybutler/nvm-windows/releases
                # 查看可安装的node版本
                nvm list\s
                # 下载安装指定版本node
                nvm install 1
                                
                # npm 切换版本
                npm install -g npm@6.1.0
                ```
                                
                ##### yarn命令
                                
                ```shell
                # 全局安装yarn
                npm i -g yarn
                # 查看当前镜像源
                yarn config get registry
                # 设置国内镜像源
                yarn config set registry https://registry.npmmirror.com
                # 初始化yarn项目
                yarn init
                # yarn的配置项
                yarn config list
                yarn config get <key>
                yarn config delete <key>
                yarn config  set <key> <value> [-g|--global]
                # 安装依赖包
                yarn install          ## 安装package.json的所有包
                yarn install --force  ## 强制重新下载所有包
                # 添加依赖包
                yarn add [package]   ## 在当前项目种添加一个依赖包,会自动更新到package.json 和yarn.lock文件种
                yarn add [package]@[version]  ## 添加指定版本的依赖
                yarn  add --dev/-D            ## 添加到devDependencies
                # 移除依赖
                yarn remove [packageName]
                # 更新依赖
                yarn upgrade [packageName]@[version]  # 如果不指定版本号，将更新到最新版本
                # 运行脚本,执行在package.json种的s'c'r
                yarn run
                # 查看一个包的所有版本
                yarn info [packageName] versions
                # 查看项目的直接依赖
                yarn list -depth=0
                ```
                                
                **MMDB问题**
                                
                ```bash
                ##打开：https://github.com/wp-statistics/GeoLite2-Country
                ## 然后下载：https://cdn.jsdelivr.net/npm/geolite2-country@1.0.2/GeoLite2-Country.mmdb.gz
                ## 或者https://gitee.com/mirrors/Pingtunnel/blob/master/GeoLite2-Country.mmdb
                ## 解压重命名文件mmdb 即可
                root@node1:~/clash# gunzip GeoLite2-Country.mmdb.gz\s
                root@node1:~/clash# mv GeoLite2-Country.mmdb Country.mmdb
                                
                ## clash for linux
                https://github.com/Dreamacro/clash/releases/download/v1.13.0/clash-linux-arm64-v1.13.0.gz
                                
                ## 启动clas for linux
                ./clash -d .
                ## or 后台启动
                nohup /clash -d . >> clash.log 2>&1 &
                ```
                                
                #### SCP（Secure Copy）
                                
                > SSH（Secure Shell）协议本身不提供文件传输功能。然而，OpenSSH（SSH的一种实现）提供了一些工具和协议，使得通过SSH进行安全的文件传输成为可能。
                                
                ```sh
                scp local_file.txt user@remote_host:/path/to/destination
                ```
                                
                #### supervisorctl
                                
                > supervisor是用Python开发的一套通用的进程管理程序，能将一个普通的命令行进程变为后台daemon（[守护进程](https://so.csdn.net/so/search?q=守护进程&spm=1001.2101.3001.7020)）。
                                
                下载supervisor
                                
                ```sh\s
                sudo apt install supervisor
                ```
                                
                常用命令
                                
                在`/etc/supervisor/conf.d`目录下，编写进程配置文件，包括一些supervisor管理进程的命令和进程启动的命令。
                                
                ```bash
                [program:java_process]
                command=/path/to/java -jar /path/to/your/java/application.jar
                autostart=true
                autorestart=true
                user=your_user
                ```
                                
                保存配置文件，更新supervisor的配置信息，启动进程。
                                
                ```bash
                supervisorctl reread
                supervisorctl update
                supervisorctl start java_process
                supervisorctl status      # 查看管理的进程信息
                ```
                                
                #### 国内源
                                
                ```bash
                go env -w GO111MODULE=on
                go env -w GOPROXY=https://goproxy.cn,direct
                ```
                                
                #### ubuntu下载docker
                                
                ```bash
                sudo apt-get install -y docker-compose
                ## 解决docker version报错
                sudo gpasswd -a <username> docker
                newgrp docker
                ```
                                
                #### ubuntu22.04下载mysql8
                                
                [ubuntu 22.04安装mysql 8.0与避坑指南-CSDN博客](https://blog.csdn.net/weixin_39636364/article/details/131234559?ops_request_misc=%7B%22request%5Fid%22%3A%22170185136516800188550802%22%2C%22scm%22%3A%2220140713.130102334..%22%7D&request_id=170185136516800188550802&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-1-131234559-null-null.142^v96^pc_search_result_base6&utm_term=ubuntu22.04安装mysql8.032&spm=1018.2226.3001.4187)
                                
                #### 切换apt国内源
                                
                vim打开`/etc/apt/sources.list`，并更换url路径。
                                
                ```bash
                https://mirrors.tuna.tsinghua.edu.cn/  ## 清华
                http://mirrors.aliyun.com/ubuntu/  ## 阿里云
                ```
                                
                > ubuntu22.04 TLS的sources.list文件
                                
                ```sh
                deb http://mirrors.aliyun.com/ubuntu/ jammy multiverse
                deb http://mirrors.aliyun.com/ubuntu/ jammy-updates multiverse
                deb http://mirrors.aliyun.com/ubuntu/ jammy-backports main restricted universe multiverse
                deb http://mirrors.aliyun.com/ubuntu jammy-security main restricted
                deb http://mirrors.aliyun.com/ubuntu jammy-security universe
                deb http://mirrors.aliyun.com/ubuntu jammy-security multiverse
                ```
                                
                sudo apt-get -y install apt-transport-https ca-certificates curl software-properties-common
                               
                """;

        System.out.println(text);
    }
}
