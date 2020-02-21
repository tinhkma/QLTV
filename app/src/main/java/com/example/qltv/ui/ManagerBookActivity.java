package com.example.qltv.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.qltv.R;
import com.example.qltv.adapter.BookAdapter;
import com.example.qltv.adapter.SlidePagerAdapter;
import com.example.qltv.managerBook.ManagerBookDrActivity;
import com.example.qltv.managerBook.StatisticalActivity;
import com.example.qltv.managerReader.UserManagerActivity;
import com.example.qltv.model.BookModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import io.realm.RealmSchema;

public class ManagerBookActivity extends AppCompatActivity implements BookItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.sldier_bar)
    ViewPager sldierBar;
    @BindView(R.id.indicator)
    TabLayout indicator;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.rcv_trending)
    RecyclerView rcvTrending;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.rcv_all)
    RecyclerView rcvAll;
    @BindView(R.id.rcv_cooming)
    RecyclerView rcvCooming;
    @BindView(R.id.views2)
    View views2;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.views3)
    View views3;
    @BindView(R.id.navigation)
    NavigationView navigation;
    @BindView(R.id.dl)
    DrawerLayout dl;
    private List<BookModel> slideList;
    private List<BookModel> bookModelList;
    private List<BookModel> bookListsTrending;
    private List<BookModel> bookModelListComingSoon;
    private SlidePagerAdapter slidePagerAdapter;
    private BookAdapter bookAdapter;
    private ActionBarDrawerToggle t;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_book);
        ButterKnife.bind(this);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .schemaVersion(0)
                .migration(new MyMigration())
                .build();
        mRealm = Realm.getDefaultInstance();

//        insertData();

        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();


        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigation.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case (R.id.mn_book): {
                    startActivity(new Intent(this, ManagerBookDrActivity.class));
                    break;
                }
                case (R.id.mn_add_delete_book): {
                    startActivity(new Intent(this, AddDeleteBookActivity.class));
                    break;
                }
                case (R.id.mn_statistical_book): {
                    startActivity(new Intent(this, StatisticalActivity.class));
                    break;
                }
                case (R.id.mn_ql_user): {
                    startActivity(new Intent(this, UserManagerActivity.class));
                    break;
                }
                case (R.id.mn_adđ_delete_user): {

                }
                case (R.id.mn_statistical_user): {

                }
                case (R.id.mn_ql_lease): {

                }
                case (R.id.mn_expiry_date): {

                }
                case (R.id.mn_report): {

                }
                case (R.id.mn_logout): {

                }

                default:
                    return true;
            }
            return false;
        });

        RealmResults<BookModel> bookModelRealmResults = mRealm.where(BookModel.class).findAll();

        slideList = new ArrayList<>();

        for (int i=0;i<5;i++){
            slideList.add(new BookModel(bookModelRealmResults.get(i).getName(), bookModelRealmResults.get(i).thumballBook));
        }

        slidePagerAdapter = new SlidePagerAdapter(this, slideList);
        sldierBar.setAdapter(slidePagerAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 3000);

        indicator.setupWithViewPager(sldierBar, true);


        RealmResults<BookModel> bookModelRealmResultsTrending = mRealm.where(BookModel.class).findAll();
        bookListsTrending = new ArrayList<>();
        bookModelListComingSoon = new ArrayList<>();
        bookModelList = new ArrayList<>();

        for (BookModel abc : bookModelRealmResultsTrending){
            if (abc.isTrending()){
                bookListsTrending.add(abc);
            }
            if (!abc.isVisible()){
                bookModelListComingSoon.add(abc);
            }
            if (abc.isVisible()){
                bookModelList.add(abc);
            }
        }

//        RecycleView All
        if (!bookModelList.isEmpty()){
            bookAdapter = new BookAdapter(this, bookModelList, this);
            LinearLayoutManager lnAll = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            rcvAll.setLayoutManager(lnAll);
            rcvAll.setAdapter(bookAdapter);
        }

        //RecycleView Trending
        if (!bookListsTrending.isEmpty()){
            bookAdapter = new BookAdapter(this, bookListsTrending, this);
            LinearLayoutManager lnTrending = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            rcvTrending.setLayoutManager(lnTrending);
            rcvTrending.setAdapter(bookAdapter);
        }

        //RecycleView Cooming Soon
        if (!bookModelListComingSoon.isEmpty()){
            bookAdapter = new BookAdapter(this, bookModelListComingSoon, this);
            LinearLayoutManager lnCommingSooon = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            rcvCooming.setLayoutManager(lnCommingSooon);
            rcvCooming.setAdapter(bookAdapter);
        }

    }

    private void insertData() {

        bookModelList = new ArrayList<>();

        bookModelList.add(new BookModel("gt001",
                "Zen and the Art of Motorcycle Maintenance",
                "Robert M. Pirsig", "Book", 100, 100,
                true, R.drawable.zen_and_the_art, R.drawable.image_01,
                "Viết về một hành trình đi khắp nước Mỹ trong mùa hè của một người cha và cậu con trai, cuốn sách Zen And The Art Of Motorcycle Maintenance còn là một hành trình triết học với đầy những câu hỏi cơ bản về cuộc sống và cách sống trên đời."
                , false, true));


        bookModelList.add(new BookModel("gt002",
                "Watership Down - Đồi Thỏ",
                "Richard Adams", "Book", 100, 100,
                true, R.drawable.watership_down, R.drawable.image_02,
                "Đồi Thỏ viết về hành trình đi tìm vùng đất mới của một nhóm các chú thỏ khi biết trước nơi chúng đang ở sẽ bị con người và máy móc phá hủy. Cuốn sách thiếu nhi này gây ngạc nhiên với người đọc bởi vì có độ dày như sách dành cho người lớn. Tuy nhiên cũng chính bởi vậy, bạn đọc đặc biệt là trẻ em có thể trải qua nhiều cuộc phiêu lưu nối tiếp cùng nhóm thỏ. Với trẻ nhỏ các em sẽ hiểu được phần nào sự nguy hiểm khi sống giữa thiên nhiên hoang dã, ý chí vượt qua khó khăn và sức mạnh của tinh thần đoàn kết, nhưng độc giả lớn tuổi đã có ít nhiều trải nghiệm cuộc sống sẽ nhìn thấy ở đó câu chuyện của cuộc sống, xã hội. Sách đã được xuất bản tại Việt Nam và chuyển thể thành phim hoạt hình cùng tên."
                , false, true));


        bookModelList.add(new BookModel("gt003",
                "The Last Lecture - Bài giảng cuối cùng",
                "Randy Pausch & Jeffrey Zaslow) Randy Pausch", "Book", 100, 100,
                true, R.drawable.the_last_lecture, R.drawable.image_03,
                "Giáo sư khoa học máy tính tại Đại học Carnegie Mellon- Mỹ, trở nên nổi tiếng sau khi đưa ra một bài giảng lạc quan mang tên \"Bài giảng cuối: Để thật sự đạt được ước mơ thời thơ ấu của bạn\" ở thời điểm ông biết mình bị ung thư tuyến tụy và chỉ có thể sống tốt trong vòng 3-6 tháng nữa. Sau thành công của bài giảng, ông cùng một người bạn viết nên cuốn sách The Last Lecture - Bài giảng cuối cùng khuyến khích mọi người tận hưởng mọi giây phút của cuộc sống. Sách đã được xuất bản tại Việt Nam."
                , false, true));


        bookModelList.add(new BookModel("gt004",
                "A Short History of Nearly Everything - Lược sử vạn vật",
                "Bill Bryson", "Book", 100, 100,
                true, R.drawable.a_short_history, R.drawable.image_04,
                "Trong cuốn sách A Short History of Nearly Everything - Lược sử vạn vật này Bill Bryson mô tả một cách đơn giản và thô mộc mọi thứ từ kích thước và lịch sử của vũ trụ cho tới lịch sử của nhân loại. Ông cũng dành thời gian nói về các nhà khảo cổ học lập dị, nhân chủng học, và các nhà toán học đã đóng góp cho những khám phá vĩ đại nhất của thế giới. A Short History of Nearly Everything- Lược sử vạn vật là cuốn sách về khoa học bán chạy nhất ở Anh năm 2005 với hơn 300,000 bản in được bán ra, đồng thời cũng là cuốn sách khoa học bán chạy nhất tại Mỹ. Cuốn sách cũng đồng thời đạt hai giải thưởng danh giá là Giải Aventis (2004) cho cuốn sách khoa học đại cương hay nhất, và giải Descartes (2005) về truyền thông khoa học. Sách đã được Alpha Books dịch và xuất bản tại Việt Nam."
                , false, true));


        bookModelList.add(new BookModel("gt005",
                "Man's Search for Meaning - Đi tìm lẽ sống",
                "Viktor Frankl", "Book", 100, 100,
                true, R.drawable.mans_search_for_meaning, R.drawable.image_02,
                "Man's Search for Meaning - Đi tìm lẽ sống được viết bởi Viktor Frankl từ chính kinh nghiệm của mình là tù nhân trong trại tập trung Auschwitz trong Thế chiến II . Với sự hiểu biết phong phú và trải nghiệm tâm lý của người tù, Frankl ngẫm lại những ý nghĩa của cuộc sống, và cách làm thế nào mà xã hội lại có những người đứng đắn và không đứng đắn."
                , false, true));

        bookModelList.add(new BookModel("gt006",
                "The Forever War",
                "Joe Haldeman", "Book", 100, 100,
                true, R.drawable.the_forever_war, R.drawable.image_01,
                "The Forever War viết về sự trở về của chiến binh miễn cưỡng William Mandella sau khi rời trái đất để chiến đấu với các chủng tộc người ngoài hành tinh bí ẩn Taurans. Nhưng vì thời gian giãn nở, nên William mới đi 10 năm, nhưng trên trái đất thì đã 700 năm trôi qua. Khi Mandella trở lại, trái đất đã biến thành một hành tinh hoàn toàn khác, khiến anh không còn nhận ra. Được viết bởi cựu binh tham gia chiến tranh Việt Nam, cuốn tiểu thuyết The Forever War là một ẩn dụ về cuộc sống của người lính sau khi tham gia cuộc chiến tranh này."
                , false, true));


        bookModelList.add(new BookModel("gt007",
                "Cosmos - Vũ trụ",
                "Carl Sagan", "Book", 100, 100,
                true, R.drawable.cosmos, R.drawable.image_03,
                "Cuốn sách Cosmos - Vũ trụ này giúp mọi độc giả, kể cả những người không có nhiều kiến thức về khoa học, cũng có thể hiểu được lịch sử 15 tỷ năm của vũ trụ của chúng ta. Sách đã được dịch và xuất bản tại Việt Nam."
                , true, true));


        bookModelList.add(new BookModel("gt008",
                "Bartleby The Scrivener: A Story of Wall-Street",
                "Herman Melville", "Book", 100, 100,
                true, R.drawable.bartleby_the_scrivener, R.drawable.image_04,
                "Bartleby The Scrivener là một tiểu thuyết ngắn viết về câu chuyện ngớ ngẩn của một người đàn ông tên Bartleby làm việc tại một công ty luật New York. Bartleby là một nhân viên tuyệt vời, cho đến một ngày anh được yêu cầu đọc lại tài liệu bất kỳ và chỉ đơn giản nói \"Tôi không muốn!\", cho đến khi câu nói đó trở thành phản ứng thụ động và làm thay đổi cuộc sống của anh sau đó."
                , true, true));


        bookModelList.add(new BookModel("gt009",
                "Maus: A Survivor's Tale",
                "Art Spiegelman", "Book", 100, 100,
                true, R.drawable.maus, R.drawable.image_01,
                "Maus là cuốn tiểu thuyết hoạt hình (graphic novel) đạt giải thưởng Pulitzer, kể về câu chuyện của một người sống sót của người Do Thái Holocaust và con trai ông, một họa sĩ truyện tranh đang cố gắng để dung hòa với câu chuyện của cha mình. Maus là sự thật ảm đạm và kinh hoàng về của cuộc sống dưới thời Hitler, cũng như câu chuyện về mối quan hệ của người con trai với người cha già của mình."
                , true, true));


        bookModelList.add(new BookModel("gt010",
                "For Whom the Bell Tolls - Chuông nguyện hồn ai",
                "Ernest Hemingway", "Book", 100, 100,
                true, R.drawable.for_whom_the_bell_tolls, R.drawable.image_03,
                "Tác phẩm truyện tranh này viết về Robert Jordan, một chuyên gia phá hủy trẻ và lý tưởng của Mỹ, chiến đấu cùng lực lượng chống phát xít trong nội chiến Tây Ban Nha năm 1937. For Whom the Bell Tolls - Chuông nguyện hồn ai diễn ra trong vòng 68 giờ, trong khi Jordan đang cố gắng tìm cách để thổi bay một cây cầu của địch, đấu tranh với các nhà lãnh đạo thụ động của lực lượng du kích, và rơi vào tình yêu với một người phụ nữ trẻ người Tây Ban Nha. Sách đã được xuất bản tại Việt Nam."
                , false, true));


        bookModelList.add(new BookModel("gt011",
                "The Little Prince - Hoàng tử bé",
                "Antoine de Saint-Exupéry", "Book", 100, 100,
                true, R.drawable.the_little_prince, R.drawable.image_04,
                "Cuốn tiểu thuyết The Little Prince - Hoàng tử bé kể về câu chuyện của một phi công bị rơi máy bay trong sa mạc Sahara và được chào đón bởi một cậu bé tuyên bố mình là một hoàng tử nhỏ, từ một hành tinh khác đến. Trong quá trình sửa chữa máy bay, viên phi công đã biết cuộc đời của \"hoàng tử nhỏ\" và khao khát trở lại hành tinh quê hương của mình. Mặc dù được coi là một cuốn truyện thiếu nhi tinh tuyển, nhưng \"Hoàng tử bé\" cũng được coi là một trong những cuốn tiểu thuyết sâu sắc nhất của văn học Pháp. The Little Prince - Hoàng tử bé được nhiều đơn vị dịch và xuất bản tại Việt Nam."
                , true, true));


        bookModelList.add(new BookModel("gt012",
                "How to Win Friends and Influence People - Đắc nhân tâm",
                "Dale Carnegie", "Book", 100, 100,
                true, R.drawable.how_to_win_friend, R.drawable.image_01,
                "How to Win Friends and Influence People - Đắc nhân tâm được viết vào những năm 1930, nhưng hầu hết các lời khuyên của Carnegie vẫn đúng với ngày hôm nay. Cuốn sách phát triển bản thân này cũng nằm trong nhiều Top sách gây ảnh hưởng và làm thay đổi cuộc sống của nhiều tờ báo, trang web uy tín. Cuốn sách thậm chí cũng nằm trong nhiều Top sách quản trị kinh doanh hay nhất mọi thời đại. How to Win Friends and Influence People - Đắc nhân tâm cũng đã được dịch và xuất bản tại Việt Nam."
                , true, true));

        bookModelList.add(new BookModel("gt013",
                "Animal Farm - Chuyện ở nông trại",
                "George Orwell", "Book", 100, 100,
                true, R.drawable.animal_arm, R.drawable.image_01,
                "Trong tác phẩm Animal Farm - Chuyện ở nông trại, George Orwell đã dùng hình tượng những con gia súc trong trang trại để thể hiện những tiên đoán của ông về nhà nước Cộng sản Sô- viết. Cuốn sách đã được chuyển ngữ và xuất bản tại Việt Nam."
                , false, false));
        bookModelList.add(new BookModel("gt014",
                "The Handmaid's Tale - Chuyện người tùy nữ",
                "Margaret Atwood", "Book", 100, 100,
                true, R.drawable.chuyen_nguoi_tuy_nu, R.drawable.image_03,
                "The Handmaid's Tale - Chuyện người tùy nữ viết về bối cảnh giả tưởng Hoa Kỳ đã trở thành Cộng hòa Gilead, ở đó một tầng lớp phụ nữ đặc biệt bị sử dụng thân xác làm công cụ duy trì nòi giống, thay cho lớp quý bà đã đánh mất hoàn toàn khả năng này. Nhân vật chính của cuốn tiểu thuyết là tùy nữ Offred. Trong hoàn cảnh nô lệ, bị tước đoạt phẩm giá cũng như mọi niềm vui sống, bị âm mưu biến thành “những cỗ tử cung có chân” và cái chết đau đớn luôn rình rập, tùy nữ của Atwood vẫn làm rung động trái tim hàng triệu độc giả vì những cảm xúc mãnh liệt, trí óc xét đoán sắc bén và độc lập, tính hài hước ngạo nghễ và lòng ham sống cuồng nhiệt, được nhấn mạnh như những phẩm chất con người. The Handmaid's Tale - Chuyện người tùy nữ đã được chuyển ngữ và xuất bản tại Việt Nam."
                , false, false));


        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bookModelList);
        mRealm.commitTransaction();
        mRealm.close();

        //List Trending

//        bookListsTrending.add(new BookModel("gt012",
//                "How to Win Friends and Influence People - Đắc nhân tâm",
//                "Dale Carnegie", "Book", 100, 100,
//                true, R.drawable.how_to_win_friend, R.drawable.image_01,
//                "How to Win Friends and Influence People - Đắc nhân tâm được viết vào những năm 1930, nhưng hầu hết các lời khuyên của Carnegie vẫn đúng với ngày hôm nay. Cuốn sách phát triển bản thân này cũng nằm trong nhiều Top sách gây ảnh hưởng và làm thay đổi cuộc sống của nhiều tờ báo, trang web uy tín. Cuốn sách thậm chí cũng nằm trong nhiều Top sách quản trị kinh doanh hay nhất mọi thời đại. How to Win Friends and Influence People - Đắc nhân tâm cũng đã được dịch và xuất bản tại Việt Nam.", true, true));
//        bookListsTrending.add(new BookModel("gt011",
//                "The Little Prince - Hoàng tử bé",
//                "Antoine de Saint-Exupéry", "Book", 100, 100,
//                true, R.drawable.the_little_prince, R.drawable.image_04,
//                "Cuốn tiểu thuyết The Little Prince - Hoàng tử bé kể về câu chuyện của một phi công bị rơi máy bay trong sa mạc Sahara và được chào đón bởi một cậu bé tuyên bố mình là một hoàng tử nhỏ, từ một hành tinh khác đến. Trong quá trình sửa chữa máy bay, viên phi công đã biết cuộc đời của \"hoàng tử nhỏ\" và khao khát trở lại hành tinh quê hương của mình. Mặc dù được coi là một cuốn truyện thiếu nhi tinh tuyển, nhưng \"Hoàng tử bé\" cũng được coi là một trong những cuốn tiểu thuyết sâu sắc nhất của văn học Pháp. The Little Prince - Hoàng tử bé được nhiều đơn vị dịch và xuất bản tại Việt Nam.", true, true));
//        bookListsTrending.add(new BookModel("gt005",
//                "Man's Search for Meaning - Đi tìm lẽ sống",
//                "Viktor Frankl", "Book", 100, 100,
//                true, R.drawable.mans_search_for_meaning, R.drawable.image_02,
//                "Man's Search for Meaning - Đi tìm lẽ sống được viết bởi Viktor Frankl từ chính kinh nghiệm của mình là tù nhân trong trại tập trung Auschwitz trong Thế chiến II . Với sự hiểu biết phong phú và trải nghiệm tâm lý của người tù, Frankl ngẫm lại những ý nghĩa của cuộc sống, và cách làm thế nào mà xã hội lại có những người đứng đắn và không đứng đắn.", true, true));
//        bookListsTrending.add(new BookModel("gt007",
//                "Cosmos - Vũ trụ",
//                "Carl Sagan", "Book", 100, 100,
//                true, R.drawable.cosmos, R.drawable.image_03,
//                "Cuốn sách Cosmos - Vũ trụ này giúp mọi độc giả, kể cả những người không có nhiều kiến thức về khoa học, cũng có thể hiểu được lịch sử 15 tỷ năm của vũ trụ của chúng ta. Sách đã được dịch và xuất bản tại Việt Nam.", true, true));


        //List Comming Soon
//        bookModelListComingSoon = new ArrayList<>();


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBookClick(BookModel books, ImageView imgBook) {
//        Intent intent = new Intent(this, DetailActivity.class);
//        intent.putExtra("title", books.getName());
//        intent.putExtra("ig_thumball", books.getCoverBook());
//        intent.putExtra("des", books.getDescription());
//        intent.putExtra("author", books.getAuthor());
//        intent.putExtra("amount", books.getAmount());
//        intent.putExtra("status", books.isVisible());
//
//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,
//                imgBook, "sharedName");
//
//        startActivity(intent, options.toBundle());
//        startActivity(intent);
    }

    class SliderTimer extends TimerTask {

        @Override
        public void run() {

            ManagerBookActivity.this.runOnUiThread(() -> {
                if (sldierBar.getCurrentItem() < slideList.size() - 1) {
                    sldierBar.setCurrentItem(sldierBar.getCurrentItem() + 1);
                } else {
                    sldierBar.setCurrentItem(0);
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private class MyMigration implements RealmMigration {

        @Override
        public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {
            // DynamicRealm exposes an editable schema
            RealmSchema schema = realm.getSchema();
        }
    }
}
