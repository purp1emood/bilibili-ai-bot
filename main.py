import requests
import time
import json
import pymysql
import re

def getmsgs():
    # 设置cookie,填自己账号的
    cookie = {
        "Cookie": "SESSDATA=12fd5a2e%2C1709523493%2Ccd431%2A927pvQlLOyva9ohEZWuB9PcpE1Cb7cfOa9tKwOor2UZ1VD8z26QYchdKqkqYmlFvRD3yK18AAAQwA; bili_jct=d663d246c600b19f21669097b5ee5936;"}
    while True:
        # 每三秒爬取一次

        star_num = 0 # 用来解析数组,最好不要动
        getmsgapi = requests.get("https://api.bilibili.com/x/msgfeed/at?build=0&mobi_app=web", headers=cookie)
        resp = getmsgapi.json()
        if resp['code'] == 0 and len(resp['data']['items']) > 0:
            print("连接api成功！")
            n = 1
            for item in resp['data']['items']:
                if n > 5:
                    break
                n += 1
                uri = str(item['item']['uri'])
                bv_number = uri.split('/')[-1]
                match = re.search(r'bili_jct=([^;]+)',cookie['Cookie'])
                csrf = match.group(1)
                msg, flag = cx_bot(bv_number)
                retype = item['item']['business_id']
                oid = item['item']['subject_id']
                root = item['item']['source_id']
                atname = item['user']['nickname']
                if flag == True:
                    get_fs_msg = getaddmsg(retype, oid, root, msg, csrf, cookie, atname)
                    if get_fs_msg == False:
                        print("调用B站api发送回复时出现错误")
                    else:
                        print("回复成功")
        else:
            print("连接api失败，请检查cookie")
        time.sleep(60 * 10)  # 防止B站风控系统封ip,有条件可以使用ip池来请求

def cx_bot(bv_number):
    try:
        botget = requests.get("http://47.88.66.62:8080/gpt/" + str(bv_number))
        re = botget.json()
        print(bv_number + ":" + re['message'])
        if re['code'] == 200:
            return re['message'], True
        else:
            return re['message'], False
    except requests.exceptions.RequestException as e:
        # 在连接失败时捕获异常
        print("连接失败:", e)
        return "连接失败，无法获取响应", False



def getaddmsg(retype, oid, root, cflendd, csrf, cookie, at_name):
    # 要post的数据
    repdatas = {"oid": oid, "type": retype, "root": root, "parent": root, "message": "@" + at_name + " " + cflendd,
                "plat": "1",
                "ordering": "time", "jsonp": "jsonp", "csrf": csrf}

    try:
        addmsg = requests.post("https://api.bilibili.com/x/v2/reply/add", headers=cookie, data=repdatas)  # 调用 B 站 API 发送回复
        addmsg_re = addmsg.json()

        if addmsg_re['code'] == 0:
            print("发送回复成功,发送内容:" + addmsg_re['data']['reply']['content']['message'])
            return True
        else:
            return False
    except requests.exceptions.RequestException as e:
        # 在连接失败时捕获异常
        print("连接失败:", e)
        return False

if __name__ == '__main__':
    getmsgs()