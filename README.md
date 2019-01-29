# TreeAdapter  [ ![Download](https://api.bintray.com/packages/plumcookingwine/plumwine/TreeRvAdapter/images/download.svg) ](https://bintray.com/plumcookingwine/plumwine/TreeRvAdapter/_latestVersion)
1、# 使用： compile ‘com.plumcookingwine.tree:TreeRvAdapter:0.0.2’;

2、实体类的创建： TreeListDao是具体封装的对象， 里面有两个泛型，第一个就是父级对象，第二个就是子级集合； 其他属性为一些配置项： 
```
// 是否可展开收起 
private boolean isEnableExpand = true; 
// 默认是否展开状态 
private boolean isExpand = false; 
// 收起状态最少展示子item数量 
private int minCount = 6;
```

目前支持这几种配置；以后会逐渐完善。。。

（举例） 


`
	data class FilterDao(   
		var _ID: Int = 0, 
		var mTitle: String = "", 
		var mDesc: String = "", 
		var mCheckOption: List?
	) 
`
`
	data class OptionDao( 
		var _ID: Int = 0, 
		var mTitle: String = "", 
		var mDesc: String = "", 
		var isCheck: Boolean = false 
	)
`


其中FilterDao 是父级对象， OptionDao是子级对象，最后将这两个对象放到TreeListDao对象中: 

```
	private fun initData(): MutableList<TreeListDao<FilterDao, OptionDao>> {   
		val list = mutableListOf<TreeListDao<FilterDao, OptionDao>>() 
		for (i in 0..5) { 
			val dao = TreeListDao<FilterDao, OptionDao>() 
			val filterDao = FilterDao(i, "title$i", "title$i", null) 
			val subs = mutableListOf() 
			for (j in 0..20) {
	 			val subListDao = OptionDao(j, "o${j}t${i}", "o${j}t${i}", false) 
				subs.add(subListDao) 
			}
	 		dao.groupDao = filterDao 
			dao.subList = subs if (i % 2 == 0) {
	 			dao.isMultiCheck = true } list.add(dao)
	 		} 
		return list 
	}
```
    

2、创建adapter继承自AbsTreeListAdapter，将TreeListDao传进来，重写方法： 
```
    	// 组item的layoutId 
	public abstract int groupLayoutId(); 
	// 子item的layoutId 
	public abstract int subLayoutId(); 
	// 绑定组item数据（K 为传进来的父对象泛型） 
	public abstract void onBindGroupHolder(GroupItemViewHolder holder, K k, int groupIndex, int position); 
	// 绑定子item数据（V 为传进来的子对象泛型） 
	public abstract void onBindSubHolder(SubItemViewHolder holder, V v, int subIndex, int groupIndex, int position);
```

## 效果
### 效果：
<img src="https://img-blog.csdnimg.cn/2019012410500962.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIyMDkwMDcz,size_16,color_FFFFFF,t_70" width = "" height="500" div align=center />
<img src="https://img-blog.csdnimg.cn/20190124142702498.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIyMDkwMDcz,size_16,color_FFFFFF,t_70" width = "" height="500" div align=center />

## 实现  具体的实现原理请看文章： 
## https://blog.csdn.net/qq_22090073/article/details/86623042
