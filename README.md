# LazyFragment

## 介绍

我们知道在ViewPager有一个重要的机制就是缓存机制，默认情况下，ViewPager会缓存它的前面一个、当前的、后面一个Item，我们通过mVp.setOffscreenPageLimit()函数来适当扩大它的缓存大小，以保障缓存更多的Item，这个时候当页面首次加载的时候就会去初始化更多的Item以保障流畅，并且我们会在Item被创建的时候去初始化一些数据，这样就会严重降低初始化的效率。这里使用LazyFragment实现懒加载，当初始化的时候我们仅仅初始化Item的容器，并不去初始化Item中的数据，每当ViewPager滑动到当前的Item后，然后再去渲染数据，并缓存，以此来提升首次初始化效率。

## 使用

### gradle依赖

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  
	dependencies {
	        implementation 'com.github.guojiel:LazyFragment:Tag'
	}
  
  //注意,你的项目中需要保证有Fragment的依赖
  
  implementation 'com.android.support:support-fragment:28.0.0'
  
  ### code编写
  
  ViewPager的配置：setOffscreenPageLimit(n)，n为ViewPager中实际Item的数量~
  
  1.继承LazyFragment
  
  2.重写方法lzViewCreate()
  
  3.在方法lzViewCreate()中 调用方法setContentView(layoutId)
  
  4.基本完成
  
  ### code其他
  
  
  #lzViewCreate()
  
  #lzViewDestroy()
  
  #lzResume()
  
  #lzPause()
  
  ### demo
  
  https://github.com/guojiel/LazyFragment/blob/master/apks/app-release.apk
  
  ## 最后说明
  
  ViewPager中AFragment、BFragment,
 
  首次进入A或B执行生命周期方法：{@link #lzViewCreate()} -> {@link #lzResume()}
 
  由A切换到B执行生命周期方法：A{@link #lzPause()} -> B{@link #lzResume()}
 
  当前A是Resume状态，启动一个Activity，执行生命周期方法：A{@link #lzPause()} B不变，再回来时，执行生命周期方法：A{@link #lzResume()} B不变
  
  
  -----
  
  
