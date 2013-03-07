package xuandu;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboParameters;
import com.weibo.sdk.android.net.RequestListener;

public class HotWordsAPI extends ServerAPI {
	 
    public HotWordsAPI(Oauth2AccessToken oauth2AccessToken) {
		super(oauth2AccessToken);
		// TODO Auto-generated constructor stub
	}
	private static final String SERVER_URL_PRIX = API_SERVER;

	/**
	 * 获取当前登录用户及其所关注用户的最新微博的ID
	 * @param page_size 单页返回的记录条数，默认为50。
	 * @param now_page 返回结果的页码，默认为1。
	 * @param base_app 是否只获取当前应用的数据。false为否（所有数据），true为是（仅当前应用），默认为false。
	 * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。
	 * @param listener
	 */
	
	public void friendsTimeline(int now_page, int page_size, int order_type, RequestListener listener){
	    WeiboParameters params = new WeiboParameters();
	    System.out.println("______________________hotWord_________friendsTimeline_______________________");
	    params.add("token", super.getToken());
        params.add("nowPage", now_page);
        params.add("pageSize", page_size);
        params.add("orderType", order_type);
        
        request( SERVER_URL_PRIX, params, HTTPMETHOD_GET, listener);
	}
	public void friendsTimeline(Long since_id, Long max_id, int now_page, int page_size, int order_type, RequestListener listener){
	    WeiboParameters params = new WeiboParameters();
	    System.out.println("______________________hotWord_________friendsTimeline_______________________");
	    params.add("token", super.getToken());
        params.add("nowPage", now_page);
        params.add("pageSize", page_size);
        params.add("orderType", order_type);
        params.add("maxId", max_id);
        params.add("since_id", since_id);
        
        request( SERVER_URL_PRIX, params, HTTPMETHOD_GET, listener);
	}
}
