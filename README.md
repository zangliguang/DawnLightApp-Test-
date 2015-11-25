

# DawnLightApp_Test
2015年10月30号，开始使用readme
勉强实现加载数据，异步请求网络还是个bug，okhttp并没有实现异步，还需要自己搞定，应该用volley或者android-async-http，还得研究

2015年11月24号
至少应该用threadpool才行，还有就是UltimateViewAdapter没有及时刷新，是个问题

2015年11月25号
UltimateRecyclerView设置滑动时候停止imageloader加载，想和listview一样流畅滑动，似乎有些问题，只要滑动，adapter 就只行onbindview，要命。
RecyclerTabLayout加载多个fragment时候，内存释放也是个问题，outScreenLimit可以设置大一些，不过也不是根本途径
网易新闻客户端的选择tab展示是个不错的选择，还得做dragGridview。
上两个截图：

![image](https://github.com/zangliguang/DawnLightApp_Test/blob/master/screen_shortcut/S51125-203652.png)
![image](https://github.com/zangliguang/DawnLightApp_Test/blob/master/screen_shortcut/S51125-203645.png)


