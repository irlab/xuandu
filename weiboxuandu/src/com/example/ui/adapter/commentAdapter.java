package com.example.ui.adapter;

import java.util.List;

import com.example.R;
import com.example.logic.ui.util.ViewHolder;
import com.example.logic.ui.util.commentViewHolder;
import com.example.ui.Comment;
import com.example.ui.Status;
import com.example.ui.logic.imaCache.Anseylodar;
import com.example.util.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class commentAdapter extends  BaseAdapter {

	 public List<Comment> comment;//微博信息
     Context mContext;//上下文
     Anseylodar ansylodar;
     
	 public commentAdapter(Context context,List<Comment> allcomment){
		 comment=allcomment;
		 mContext=context;
		 ansylodar=new Anseylodar();
	 }
	@Override
	public int getCount() {
		//由于我们最后一项时更多 所以这里getcount+1
		return comment.size();
	}
	@Override
	public Object getItem(int arg0) {
		return comment.get(arg0);
	}
	
    //请求更多的数据
	public void addmoreData(List<Comment> addmore){
		if (comment!=null) {
			this.comment.addAll(addmore);//吧新传得数据加到现在的list中
			this.notifyDataSetChanged();//将数据追加到ListView中显示
		}
	}
	@Override
	public long getItemId(int index) {
			return Long.valueOf( comment.get(index).getId() );
	}

	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
	public View getView(int position, View conterView, ViewGroup arg2) {
		
		View statusView=null;
		if ((conterView!=null)&&(conterView.findViewById(R.id.contentPic)!=null)) {
			statusView=conterView;
		}else {//如果缓存中没有就重新创建
			//加载一个新的View当 Root为null时可以拿到所有XML的资源文件
			statusView=LayoutInflater.from(mContext).inflate(R.layout.commentview, null);
		}
		commentViewHolder vHolder=null;
		vHolder=new commentViewHolder();
		vHolder.tvCommentName=(TextView) statusView.findViewById(R.id.tvCommentName);
		vHolder.tvCommentContent=(TextView)statusView.findViewById(R.id.tvCommentContent);
		vHolder.ivCommentPorprait=(ImageView)statusView.findViewById(R.id.ivCommentPortrait);
		vHolder.tvCommentTimeData = (TextView) statusView.findViewById(R.id.tvCommentTime);
	//	vHolder.tvCommentTimeData=(TextView)statusView.findViewById(R.id.tvCommentDate);
		//vHolder.subLayoutView=(LinearLayout) statusView.findViewById(R.id.subLayout);
		//vHolder.tvitemSubcontent=(TextView) statusView.findViewById(R.id.tvItemSubContent);
		//vHolder.contentPic=(ImageView)statusView.findViewById(R.id.contentPic);
		//vHolder.subContenPic=(ImageView)statusView.findViewById(R.id.subContentPic);
	//	vHolder.tvCommentFrom =(TextView)statusView.findViewById(R.id.tvCommentFrom); 
		
		Comment mcomment = comment.get(position);

	//	if (mstatus.getUser().isVerified()) {
//			vHolder.ivitemV=(ImageView) statusView.findViewById(R.id.ivItemV);
//			vHolder.ivitemV.setVisibility(View.VISIBLE);
	//	}
		//设定发表微博的用户的昵称 
		vHolder.tvCommentName.setText(mcomment.getUser().getName());
		//设定内容
		vHolder.tvCommentContent.setText(mcomment.getText());
		
		vHolder.tvCommentTimeData.setText(Utility.showTime(mcomment.getCreatedAt()));
		
	//	vHolder.tvCommentFrom.setText(" From: " + mcomment.getSource().getName());
		//设定表发微博的时间
		//mstatus
//		vHolder.tvCommentTimeData.setText(mstatus.getCreatedAt().toLocaleString().substring(13, 20));
		//加载用户头像
		
		String usericon = mcomment.getUser().getProfileImageURL().toString();
		ansylodar.showimgAnsy(vHolder.ivCommentPorprait, usericon);
		//判断是否又转发
//		if (mstatus.getRetweetedStatus()!=null) {
//			vHolder.contentPic.setVisibility(View.GONE);
//			vHolder.subLayoutView.setVisibility(View.VISIBLE);//设置有转发布局可见
//		    Status comentsStatus=status.get(position).getRetweetedStatus();//获取又转发内容
//			vHolder.tvitemSubcontent.setText(comentsStatus.getText());
//		    String subconpic=comentsStatus.getThumbnailPic();
//		  //  comentsStatus.getThumbnailPic()
//		    if (null!=subconpic) {
//		    	vHolder.subContenPic.setVisibility(View.VISIBLE);//设置又转发图片可见
//				ansylodar.showimgAnsy(vHolder.subContenPic, subconpic);
//		    }
//		}else {//没有又转发 我们设置当前微博内容图片可见
//			String pic_path=mstatus.getThumbnailPic();
//			if (null!=pic_path) {
//				   vHolder.contentPic.setVisibility(View.VISIBLE);
//			       ansylodar.showimgAnsy(vHolder.contentPic, pic_path);
//		    }
//			vHolder.contentPic.setVisibility(View.GONE);
//			vHolder.subLayoutView.setVisibility(View.GONE);//设置又转发布局不可见
//		}
		
		return statusView;
	}
		 

	}
