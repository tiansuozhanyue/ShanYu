package com.example.shanyu.main.home.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.util.Util;
import com.example.shanyu.db.HistoryDBHelper;
import com.example.shanyu.http.HttpResultInterface;
import com.example.shanyu.http.HttpUtil;
import com.example.shanyu.main.chat.ChatActivity;
import com.example.shanyu.main.chat.EaseHelper;
import com.example.shanyu.main.home.adapter.BookInfoAddressAdapter;
import com.example.shanyu.main.home.adapter.CommentAdapter;
import com.example.shanyu.main.home.bean.BookInfoMode;
import com.example.shanyu.main.home.bean.CommentBean;
import com.example.shanyu.main.home.bean.ShareBean;
import com.example.shanyu.main.mine.bean.AddressMode;
import com.example.shanyu.main.mine.bean.MyBooksMode;
import com.example.shanyu.main.mine.ui.AddressActivity;
import com.example.shanyu.main.mine.ui.SelectAddressActivity;
import com.example.shanyu.main.mine.ui.SetAddressActivity;
import com.example.shanyu.utils.AppUtil;
import com.example.shanyu.utils.SharedUtil;
import com.example.shanyu.utils.ToastUtil;

import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.shanyu.R;
import com.example.shanyu.base.BaseActivity;
import com.example.shanyu.http.HttpApi;
import com.example.shanyu.utils.ImageLoaderUtil;
import com.example.shanyu.utils.WeChatShareUtil;
import com.example.shanyu.widget.MyListView;
import com.example.shanyu.widget.ShopSumButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.to.aboomy.banner.Banner;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookInfoActivity extends BaseActivity implements BookInfoAddressAdapter.AddressOnClick, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.cover)
    public Banner cover;
    @BindView(R.id.name)
    public TextView name;
    @BindView(R.id.price)
    public TextView price;
    @BindView(R.id.price1)
    public TextView price1;
    @BindView(R.id.def_address)
    public TextView def_address;
    @BindView(R.id.address_select)
    public Switch address_select;
    @BindView(R.id.switch_text)
    public TextView switch_text;
    @BindView(R.id.collection)
    public TextView collection;
    @BindView(R.id.mWebView)
    public WebView mWebView;
    @BindView(R.id.myListView)
    public MyListView myListView;

    HistoryDBHelper historyDBHelper;
    TextView dialogAddress;
    String bookModeId;
    BookInfoMode bookMode;
    ShareBean shareBean;
    AddressMode addressMode;
    List<AddressMode> addressModes;
    Dialog addressDialog;
    int isselected = 0;
    int collectionStaue;
    int shop_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmersiveStatusBar(true);
        setContentView(R.layout.activity_book_info);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        historyDBHelper = HistoryDBHelper.getInstance(this);
        bookModeId = getIntent().getStringExtra("bookModeId");
        address_select.setOnCheckedChangeListener(this);
        getBookInfo();
        getAddress1();
        getComment();
        getShareInfo();
    }

    @OnClick({R.id.goback, R.id.share, R.id.shop,
            R.id.chat, R.id.buy, R.id.add,
            R.id.address_layout, R.id.comment_more,
            R.id.collection})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.goback:
                finish();
                break;

            case R.id.share:
                showShareDialog();
                break;

            case R.id.shop:
                Intent intent1 = new Intent(BookInfoActivity.this, ShopSearchActivity.class);
                intent1.putExtra("shop_id", bookMode.getShop_id() + "");
                intent1.putExtra("uid", bookMode.getUid() + "");
                startActivity(intent1);
                break;
            case R.id.chat:
                EaseHelper.getInstance().goChat(this, bookMode.getUid() + "",bookMode.getName(),HttpApi.HOST + bookMode.getCovers());
                break;

            case R.id.buy:
                bugBookDialog();
                break;

            case R.id.add:
                addCart();
                break;

            case R.id.comment_more:
                Intent intent2 = new Intent(BookInfoActivity.this, BookCommentActivity.class);
                intent2.putExtra("id", bookModeId);
                startActivity(intent2);
                break;

            case R.id.address_layout:
                if (addressModes == null) {
                    startActivityForResult(new Intent(this, SetAddressActivity.class), 10);
                } else {
                    addressSelctDialog();
                }
                break;

            case R.id.collection:
                if (collectionStaue == 0) {
                    collection();
                } else {
                    cancleCollection();
                }
                break;
        }
    }

    /**
     * ????????????
     */
    private void showShareDialog() {
        Dialog shareDialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_share, null, false);
        view.findViewById(R.id.share_weixin).setOnClickListener(v -> {
            WeChatShareUtil.setShare(
                    BookInfoActivity.this,
                    0,
                    shareBean.getUrl(),
                    shareBean.getTitle(),
                    "    " + shareBean.getGoods(),
                    HttpApi.HOST + shareBean.getCovers()
            );
        });
        view.findViewById(R.id.share_weibo).setOnClickListener(v -> ToastUtil.shortToast("????????????"));
        view.findViewById(R.id.share_pyq).setOnClickListener(v -> {
            WeChatShareUtil.setShare(
                    BookInfoActivity.this,
                    1,
                    shareBean.getUrl(),
                    shareBean.getTitle(),
                    "    " + shareBean.getGoods(),
                    HttpApi.HOST + shareBean.getCovers()
            );
        });
        view.findViewById(R.id.share_qq).setOnClickListener(v -> ToastUtil.shortToast("QQ??????"));
        view.findViewById(R.id.share_cancle).setOnClickListener(v -> shareDialog.dismiss());
        shareDialog.setContentView(view);
        WindowManager.LayoutParams lp = shareDialog.getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        shareDialog.getWindow().setAttributes(lp);
        shareDialog.getWindow().setBackgroundDrawable(null);
        shareDialog.show();
    }

    /**
     * ????????????????????????
     */
    private void addressSelctDialog() {
        addressDialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_address_select, null, false);

        ListView mListView = view.findViewById(R.id.mListView);
        TextView title = view.findViewById(R.id.title);
        title.setText(isselected == 0 ? "??????????????????" : "??????????????????");

        BookInfoAddressAdapter mAddressAdapter = new BookInfoAddressAdapter(this, addressMode.getId(), addressModes, BookInfoActivity.this);
        mListView.setAdapter(mAddressAdapter);
        addressDialog.setContentView(view);
        WindowManager.LayoutParams lp = addressDialog.getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.height = AppUtil.getScreenWH(this)[1] * 65 / 100;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        addressDialog.getWindow().setAttributes(lp);
        addressDialog.getWindow().setBackgroundDrawable(null);
        addressDialog.show();
    }

    /**
     * ??????????????????
     */
    private void bugBookDialog() {
        Dialog shareDialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_buy, null, false);
        String path = HttpApi.HOST + bookMode.getCovers();
        ImageLoaderUtil.loadImage(path, view.findViewById(R.id.imag));
        ((TextView) view.findViewById(R.id.name)).setText(bookMode.getGoods());
        String[] sum = bookMode.getPreevent().split("\\.");
        ((TextView) view.findViewById(R.id.price1)).setText(sum[0]);
        ((TextView) view.findViewById(R.id.price2)).setText("." + sum[1]);
        dialogAddress = view.findViewById(R.id.address);
        if (addressMode != null) {
            dialogAddress.setSelected(true);
            dialogAddress.setText(addressMode.getAreaname() + addressMode.getAddress());
        }
        TextView price3 = view.findViewById(R.id.price3);
        price3.setText(bookMode.getPrice());
        price3.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        view.findViewById(R.id.commit).setSelected(true);
        view.findViewById(R.id.commit).setOnClickListener(v -> {
            shareDialog.dismiss();
            int num = ((ShopSumButton) view.findViewById(R.id.mShopSumButton)).getSum();
            List<MyBooksMode> myBooksModes = new ArrayList<>();
            List<MyBooksMode.ListDTO> listDTOS = new ArrayList<>();
            listDTOS.add(new MyBooksMode.ListDTO(bookMode.getId(), num, bookMode.getPreevent(), bookMode.getPrice(), bookMode.getCovers(), bookMode.getTitle()));
            myBooksModes.add(new MyBooksMode(bookMode.getShop_id(), bookMode.getName(), listDTOS));
            BookOrderActivity.start(BookInfoActivity.this, addressMode, myBooksModes, isselected + "", bookMode.getUid() + "");
        });
        view.findViewById(R.id.address_layout).setOnClickListener(v -> {
            Intent intent = new Intent(BookInfoActivity.this, AddressActivity.class);
            intent.putExtra("SeletAddress", true);
            startActivityForResult(intent, 100);
        });
        shareDialog.setContentView(view);
        WindowManager.LayoutParams lp = shareDialog.getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        shareDialog.getWindow().setAttributes(lp);
        shareDialog.getWindow().setBackgroundDrawable(null);
        shareDialog.show();
    }

    /**
     * ??????????????????
     */
    private void getBookInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("id", bookModeId);
        map.put("uid", SharedUtil.getIntence().getUid());
        showLoading();
        HttpUtil.doGet(HttpApi.BOOKINFO, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();
                bookMode = new Gson().fromJson(resultData, BookInfoMode.class);

                historyDBHelper.openWriteLink();
                historyDBHelper.insert(bookMode.getId() + "", bookMode.getCovers(), bookMode.getPrice());
                historyDBHelper.closeLink();

                shop_id = bookMode.getShop_id();

                cover.setHolderCreator((context, index, o) -> {
                    ImageView banner = new ImageView(context);
                    banner.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    BookInfoMode.PathListDTO bannerDTO = (BookInfoMode.PathListDTO) o;
                    ImageLoaderUtil.loadImage((HttpApi.HOST + bannerDTO.getPath()), banner, 1.0f);
                    return banner;
                }).setPages(bookMode.getPathList());

                name.setText(bookMode.getGoods());
                price.setText(bookMode.getPreevent());
                price1.setText("???" + bookMode.getPrice());
                price1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                collectionStaue = bookMode.getCollection();
                collection.setSelected(collectionStaue == 1);

                //????????????
                mWebView.loadUrl(bookMode.getUrl());

                //??????WebSettings??????
                WebSettings webSettings = mWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setUseWideViewPort(true); //????????????????????????webview?????????
                webSettings.setLoadWithOverviewMode(true); // ????????????????????????
                webSettings.setSupportZoom(true); //????????????????????????true??????????????????????????????
                webSettings.setBuiltInZoomControls(true); //????????????????????????????????????false?????????WebView????????????
                webSettings.setDisplayZoomControls(false); //???????????????????????????
                webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //??????webview?????????
                webSettings.setAllowFileAccess(true); //????????????????????????
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //????????????JS???????????????
                webSettings.setLoadsImagesAutomatically(true); //????????????????????????
                webSettings.setDefaultTextEncodingName("utf-8");//??????????????????

                //???????????????????????????????????????????????????????????????????????????WebView??????????????????????????????
                mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        //??????WebView????????????url
                        view.loadUrl(url);
                        //??????true
                        return true;
                    }
                });

                getAddress2();

            }
        });

    }

    /**
     * ??????????????????
     */
    private void getComment() {
        Map<String, String> map = new HashMap<>();
        map.put("id", bookModeId);
        HttpUtil.doGet(HttpApi.EVALUATE, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onSuccess(String resultData) {

                List<CommentBean> comments = new Gson().fromJson(resultData, new TypeToken<List<CommentBean>>() {
                }.getType());

                myListView.setAdapter(new CommentAdapter(BookInfoActivity.this, comments, true));

            }
        });

    }

    /**
     * ??????????????????
     */
    private void getShareInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("goods_id", bookModeId);
        HttpUtil.doGet(HttpApi.SHARE, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onSuccess(String resultData) {
                shareBean = new Gson().fromJson(resultData, ShareBean.class);
            }
        });

    }

    /**
     * ????????????
     */
    private void collection() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("goods_id", bookModeId);
        HttpUtil.doGet(HttpApi.COLLECTION, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                collectionStaue = 1;
                collection.setSelected(true);
            }
        });

    }

    /**
     * ??????????????????
     */
    private void cancleCollection() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("goods_id", bookModeId);
        HttpUtil.doGet(HttpApi.CANCLE_COLLECTION, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                collectionStaue = 0;
                collection.setSelected(false);
            }
        });

    }

    /**
     * ??????????????????(??????)
     */
    private void getAddress1() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("isselected", "0");
        HttpUtil.doGet(HttpApi.ADDRESS, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {

            }

            @Override
            public void onSuccess(String resultData) {
                addressModes = new Gson().fromJson(resultData, new TypeToken<List<AddressMode>>() {
                }.getType());
                for (AddressMode mode : addressModes) {
                    if (mode.getIsselected() == 1)
                        addressMode = mode;
                }
                if (addressMode != null) {
                    def_address.setSelected(true);
                    def_address.setText(addressMode.getAreaname() + addressMode.getAddress());
                }

            }
        });

    }

    /**
     * ??????????????????????????????
     */
    private void getAddress2() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", bookMode.getUid() + "");
        map.put("isselected", "1");
        HttpUtil.doGet(HttpApi.ADDRESS, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                address_select.setVisibility(View.GONE);
                switch_text.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess(String resultData) {
                address_select.setVisibility(View.VISIBLE);
                switch_text.setVisibility(View.VISIBLE);
                if (isselected == 0)
                    return;
                addressModes = new Gson().fromJson(resultData, new TypeToken<List<AddressMode>>() {
                }.getType());
                addressMode = addressModes.get(0);
                if (addressMode != null) {
                    def_address.setSelected(true);
                    def_address.setText(addressMode.getAreaname() + addressMode.getAddress());
                }

            }
        });

    }

    /**
     * ???????????????
     */
    private void addCart() {

        if (addressMode == null) {
            ToastUtil.shortToastMid("????????????????????????");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("uid", SharedUtil.getIntence().getUid());
        map.put("goods_id", bookMode.getId() + "");
        map.put("buy_num", "1");
        map.put("ty", "0");
        map.put("addressid", addressMode.getId() + "");
        showLoading();
        HttpUtil.doPost(HttpApi.ADDCART, map, new HttpResultInterface() {
            @Override
            public void onFailure(String errorMsg) {
                dismissLoading();
            }

            @Override
            public void onSuccess(String resultData) {
                dismissLoading();
                ToastUtil.shortToast("????????????");
            }
        });

    }

    @Override
    public void onAddressSelet(AddressMode mode) {

        if (addressDialog != null && addressDialog.isShowing())
            addressDialog.dismiss();

        addressMode = mode;
        if (addressMode != null) {
            def_address.setSelected(true);
            def_address.setText(addressMode.getAreaname() + addressMode.getAddress());
            if (dialogAddress != null) {
                dialogAddress.setSelected(true);
                dialogAddress.setText(addressMode.getAreaname() + addressMode.getAddress());
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isselected = isChecked ? 1 : 0;
        if (isChecked) {
            getAddress2();
        } else {
            getAddress1();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 200) {
            getAddress1();
        } else if (requestCode == 100 && resultCode == 101) {
            addressMode = (AddressMode) data.getSerializableExtra("AddressMode");
            if (addressMode != null) {
                def_address.setSelected(true);
                def_address.setText(addressMode.getAreaname() + addressMode.getAddress());
                dialogAddress.setSelected(true);
                dialogAddress.setText(addressMode.getAreaname() + addressMode.getAddress());
            }
        }
    }

}