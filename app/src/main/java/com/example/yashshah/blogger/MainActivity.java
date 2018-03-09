package com.example.yashshah.blogger;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {
    Blogpost_array_adapter blogpost_array_adapter;
    ArrayList<String> arrayList_Categories = new ArrayList<>();
    ArrayList<String> arrayList_Categories_Images = new ArrayList<>();
    ArrayList<String> arrayList_UserName = new ArrayList<>();
    ArrayList<String> arrayList_Categorory = new ArrayList<>();
    ArrayList<String> arrayList_Date = new ArrayList<>();
    ArrayList<String> arrayList_post = new ArrayList<>();
    ArrayList<String> arrayList_Imagelink = new ArrayList<>();
    ArrayList<PostElement> arrayList_blogpost = new ArrayList<>();

    private DatabaseReference mDatabase;

    int RESULT_LOAD = 100;
    int RESULT_LOAD_1 = 200;
    int RESULT_LOAD_2 = 300;
    int RESULT_LOAD_3 = 400;
    ProgressDialog pd;
    String imageurl,imageurlSecondary3,imageurlSecondary2,imageurlSecondary1,primaryImageUrl,SecondaryImageUrl1,SecondaryImageUrl2,SecondaryImageUrl3;
    ImageView PrimaryImage, SecondaryImage1, SecondaryImage2, SecondaryImage3;
    Uri selectedImageSecondary3,selectedImageSecondary2,selectedImageSecondary1,selectedImage;
    StorageReference storageRef;
    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://socio-2c3aa.appspot.com");
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);

        arrayList_Categories.add("SPORTS");
        arrayList_Categories.add("ENTERTAINMENT");
        arrayList_Categories.add("LIFESTYLE");
        arrayList_Categories.add("HEALTH");
        arrayList_Categories.add("POLITICS");
        arrayList_Categories.add("TRAVEL");
        arrayList_Categories.add("GLAMOUR");
        arrayList_Categories.add("DEALS");

        arrayList_Categories_Images.add("http://im.rediff.com/sports/2016/oct/20lio.jpg");
        arrayList_Categories_Images.add("https://clubringwood.files.wordpress.com/2013/12/entertainment.jpg");
        arrayList_Categories_Images.add("https://avada.theme-fusion.com/lifestyle/wp-content/uploads/sites/25/2015/03/blog2-compressor.jpg");
        arrayList_Categories_Images.add("http://tydenzen.cz/wp-content/uploads/2017/05/tydenzen.cz_fertility-ivf_eu_01.jpg");
        arrayList_Categories_Images.add("https://southasianvoices.org/wp-content/uploads/2014/06/16176065267_7f024ef343_o.jpg");
        arrayList_Categories_Images.add("http://ste.india.com/sites/default/files/2016/11/18/547790-birthday.jpg");
        arrayList_Categories_Images.add("http://top10wala.in/wp-content/uploads/2015/02/alia-bhatt.jpg");
        arrayList_Categories_Images.add("https://www.shoptab.net/blog/wp-content/uploads/2013/06/Daily-deal.jpg");

        arrayList_UserName.add("Yash Shah");
        arrayList_UserName.add("Mahesh Singh");
        arrayList_UserName.add("Mohan Singh");
        arrayList_UserName.add("Rohan Kapoor");
        arrayList_UserName.add("Yash Shah");
        arrayList_UserName.add("Ramesh Kapoor");
        arrayList_UserName.add("Rahul Agrawal");
        arrayList_UserName.add("Kawaljeet Singh");
        arrayList_UserName.add("Deepak Chodhary");
        arrayList_UserName.add("Sonia Raina");
        arrayList_UserName.add("Sonia Sharma");

        arrayList_Categorory.add("SPORTS");
        arrayList_Categorory.add("HEALTH");
        arrayList_Categorory.add("POLITICS");
        arrayList_Categorory.add("DEALS");
        arrayList_Categorory.add("SPORTS");
        arrayList_Categorory.add("GLAMOUR");
        arrayList_Categorory.add("TRAVEL");
        arrayList_Categorory.add("GLAMOUR");
        arrayList_Categorory.add("ENTERTAINMENT");
        arrayList_Categorory.add("LIFESTYLE");
        arrayList_Categorory.add("GLAMOUR");

        arrayList_post.add("Ravi Shastri replaces Anil Kumble as Indian cricket team's head coach, claim reports");
        arrayList_Imagelink.add("https://static.sportzwiki.com/2016/06/i.jpg");

        arrayList_post.add("Your Sense Of Smell May Be Making You Fat");
        arrayList_Imagelink.add("http://s.hswstatic.com/gif/lose-belly-fat-1.jpg");

        arrayList_post.add("Emmanuel Macron 'did not breach protocol by pushing to front of G20,' say the French");
        arrayList_Imagelink.add("http://images.financialexpress.com/2017/07/modi-reu.jpg");

        arrayList_post.add("Amazon Prime Sale is On, Discounts over a Range of Products ");
        arrayList_Imagelink.add("http://marketingland.com/wp-content/ml-loads/2014/08/amazon-orange-1920.png");

        arrayList_post.add("Zimbabwe beat Sri Lanka in 5th ODI to create History");
        arrayList_Imagelink.add("http://icc-live.s3.amazonaws.com/cms/media/images/650x433/66376.jpg");

        arrayList_post.add("Guess What Role Is Priyanka Chopra Playing In Her 3RD Hollywood Film, Isn't It Romantic?");
        arrayList_Imagelink.add("http://mediaresources.idiva.com/media/content/2017/Jan/priyanka-chopra_640x480_714774717841.jpg");

        arrayList_post.add("Travelling Increase Intelligence, claim reports");
        arrayList_Imagelink.add("http://get-a-wingman.com/wp-content/uploads/2016/01/sea-sky-beach-holiday-11.jpg");

        arrayList_post.add("Ranveer Proves that No Man Can Carry off a Skirt Better Than Him");
        arrayList_Imagelink.add("http://www.bollywoodlife.com/wp-content/uploads/2016/09/CtYBYkVXYAE63hf.jpg");

        arrayList_post.add("Movie Review: Paresh Rawal’s Guest Iin London Raises An Unbearable Stink");
        arrayList_Imagelink.add("http://static.koimoi.com/wp-content/new-galleries/2017/05/frankly-tu-sona-nachdi-song-guest-iin-london-1.jpg");

        arrayList_post.add("Student busts 'weight loss equals happiness' myth with before-and-after photo");
        arrayList_Imagelink.add("http://www.trimmedandtoned.com/wp-content/uploads/2015/07/1391242_594998710549544_1147080334_n.jpg");

        arrayList_post.add("Ranbir Kapoor Makes 8 Interesting Revelations On Jagga Jasoos, Sanjay Dutt And Much More");
        arrayList_Imagelink.add("http://ste.india.com/sites/default/files/styles/zm_700x400/public/2014/11/09/290288-ranbir-kapoor-new-700.jpg");

        arrayList_Date.add("2 Hours Ago");
        arrayList_Date.add("1 Day Ago");
        arrayList_Date.add("2 Day Ago");
        arrayList_Date.add("1 Hours Ago");
        arrayList_Date.add("4 Hours Ago");
        arrayList_Date.add("4 Day Ago");
        arrayList_Date.add("9 Hours Ago");
        arrayList_Date.add("23 Min Ago");
        arrayList_Date.add("1 Week Ago");
        arrayList_Date.add("1 Month Ago");
        arrayList_Date.add("1 Year Ago");

        for (int i = 0; i < arrayList_Date.size(); i++) {
            String Name = arrayList_UserName.get(i).toString();
            String Category = arrayList_Categorory.get(i).toString();
            String timestamp = arrayList_Date.get(i).toString();
            String Post = arrayList_post.get(i).toString();
            String Image_link = arrayList_Imagelink.get(i).toString();
            System.out.println(Name + "----" + Category + "----");
            PostElement postElement = new PostElement(Name, Category, timestamp, Post, Image_link);
            arrayList_blogpost.add(postElement);
        }

        blogpost_array_adapter = new Blogpost_array_adapter(getApplicationContext(), arrayList_blogpost);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

            collapsingToolbar.setTitle(getString(R.string.expand));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                View view = null;
                if (position == 0) {
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.layout_feed, null, false);
                    ListView listView = (ListView) view.findViewById(R.id.listView_feed);
                    listView.setAdapter(blogpost_array_adapter);
                    container.addView(view);

                    //Floating Action Button
                    FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog dialog = new Dialog(MainActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            // Include dialog.xml file
                            dialog.setContentView(R.layout.add_post_layout);
                            dialog.show();
                            final TextView titleOfThePost = (TextView) dialog.findViewById(R.id.titleofpost);
                            final TextView PostContent = (TextView) dialog.findViewById(R.id.postContent);
                            Button UploadBlog = (Button) dialog.findViewById(R.id.UploadPost);
                            PrimaryImage = (ImageView) dialog.findViewById(R.id.Primary_Image);
                            SecondaryImage1 = (ImageView) dialog.findViewById(R.id.secondary1);
                            SecondaryImage2 = (ImageView) dialog.findViewById(R.id.secondary2);
                            SecondaryImage3 = (ImageView) dialog.findViewById(R.id.secondary3);
                            final Spinner spinner=(Spinner)dialog.findViewById(R.id.spinner);
                            ArrayAdapter arrayAdapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_spinner_item,arrayList_Categories);
                            spinner.setAdapter(arrayAdapter);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            PrimaryImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    // Start the Intent
                                    startActivityForResult(galleryIntent, RESULT_LOAD);
                                }
                            });

                            SecondaryImage1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    // Start the Intent
                                    startActivityForResult(galleryIntent, RESULT_LOAD_1);
                                }
                            });

                            SecondaryImage2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    // Start the Intent
                                    startActivityForResult(galleryIntent, RESULT_LOAD_2);
                                }
                            });

                            SecondaryImage3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    // Start the Intent
                                    startActivityForResult(galleryIntent, RESULT_LOAD_3);
                                }
                            });
                            UploadBlog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    UploadImageToStorage();
                                    String title=titleOfThePost.getText().toString();
                                    String content=PostContent.getText().toString();
                                    String Category=spinner.getSelectedItem().toString();
                                    String PrimaryImageUrl=primaryImageUrl;
                                    String SecondaryImage1=SecondaryImageUrl1;
                                    String SecondaryImage2=SecondaryImageUrl2;
                                    String SecondaryImage3=SecondaryImageUrl3;
                                    PostElement postElement=new PostElement("Yash Shah",title,Category,"Today",content,PrimaryImageUrl,SecondaryImage1,SecondaryImage2,SecondaryImage3);
                                    String Id=mDatabase.push().getKey();
                                    mDatabase.child("posts").child(Id).setValue(postElement);
                                    pd.dismiss();
                                }
                            });
                        }
                    });
                }

                if (position == 1) {
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.layout_categories, null, false);
                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_Categories);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    ArrayAdapterCategroies arrayAdapterCategroies = new ArrayAdapterCategroies(MainActivity.this, arrayList_Categories, arrayList_Categories_Images);
                    recyclerView.setAdapter(arrayAdapterCategroies);
                    container.addView(view);


                }

                if (position == 2) {
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.layout_profile, null, false);

                    container.addView(view);
                }

                if (position == 3) {
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.layout_favourite, null, false);
                    container.addView(view);
                }

                if (position == 4) {
                    view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.layout_notification, null, false);
                    container.addView(view);
                }

                return view;
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_feed),
                        Color.parseColor(colors[0])
                ).title("Heart")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_categories),
                        Color.parseColor(colors[1])
                ).title("Cup")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_profile),
                        Color.parseColor(colors[2])
                ).title("Diploma")
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[3])
                ).title("Flag")
                        .badgeTitle("icon")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_notification),
                        Color.parseColor(colors[4])
                ).title("Medal")
                        .badgeTitle("777")
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);

        navigationTabBar.setBehaviorEnabled(true);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imageurl = cursor.getString(columnIndex);
                cursor.close();
                Glide.with(MainActivity.this).load(imageurl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(PrimaryImage);

            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went embrassing", Toast.LENGTH_LONG)
                    .show();
        }

        //Secondary Images
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_1 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                selectedImageSecondary1= data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImageSecondary1,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imageurlSecondary1 = cursor.getString(columnIndex);
                cursor.close();
                SecondaryImage1.setBackground(null);
                Glide.with(MainActivity.this).load(imageurlSecondary1)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(SecondaryImage1);

            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went embrassing", Toast.LENGTH_LONG)
                    .show();
        }

        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_2 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                selectedImageSecondary2 = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImageSecondary2,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imageurlSecondary2 = cursor.getString(columnIndex);
                cursor.close();
                SecondaryImage2.setBackground(null);
                Glide.with(MainActivity.this).load(imageurlSecondary2)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(SecondaryImage2);

            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went embrassing", Toast.LENGTH_LONG)
                    .show();
        }

        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_3 && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                selectedImageSecondary3 = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImageSecondary3,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imageurlSecondary3 = cursor.getString(columnIndex);
                cursor.close();
                SecondaryImage3.setBackground(null);
                Glide.with(MainActivity.this).load(imageurlSecondary3)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(SecondaryImage3);

            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went embrassing", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void UploadImageToStorage()
    {
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        if(selectedImage != null) {
            pd.show();
            Double numberFront=Math.random()*100;
            Double numberBack=Math.random()*120;
            String ImageName=numberFront+"image"+numberBack+".jpg";
            StorageReference childRef = storageRef.child(ImageName);

            //uploading the image
            UploadTask uploadTask = childRef.putFile(selectedImage);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.show();
                    Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    @SuppressWarnings("VisibleForTests")
                    Uri primaryImageUri=taskSnapshot.getDownloadUrl();
                    Toast.makeText(getApplicationContext(),"Image Url--"+primaryImageUri.toString(),Toast.LENGTH_SHORT);
                    primaryImageUrl=primaryImageUri.toString();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(MainActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(selectedImageSecondary1 != null) {
            Double numberFront=Math.random()*100;
            Double numberBack=Math.random()*120;
            String ImageName=numberFront+"image"+numberBack+".jpg";
            StorageReference childRef = storageRef.child(ImageName);

            //uploading the image
            UploadTask uploadTask = childRef.putFile(selectedImageSecondary1);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    @SuppressWarnings("VisibleForTests")
                    Uri primaryImageUri=taskSnapshot.getDownloadUrl();
                    SecondaryImageUrl1=primaryImageUri.toString();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(MainActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(selectedImageSecondary2 != null) {
            Double numberFront=Math.random()*100;
            Double numberBack=Math.random()*120;
            String ImageName=numberFront+"image"+numberBack+".jpg";
            StorageReference childRef = storageRef.child(ImageName);

            //uploading the image
            UploadTask uploadTask = childRef.putFile(selectedImageSecondary2);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    @SuppressWarnings("VisibleForTests")
                    Uri primaryImageUri=taskSnapshot.getDownloadUrl();
                    SecondaryImageUrl2=primaryImageUri.toString();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(MainActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(selectedImageSecondary3 != null) {
            pd.show();
            Double numberFront=Math.random()*100;
            Double numberBack=Math.random()*120;
            String ImageName=numberFront+"image"+numberBack+".jpg";
            StorageReference childRef = storageRef.child(ImageName);

            //uploading the image
            UploadTask uploadTask = childRef.putFile(selectedImageSecondary3);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    @SuppressWarnings("VisibleForTests")
                    Uri primaryImageUri=taskSnapshot.getDownloadUrl();
                    SecondaryImageUrl3=primaryImageUri.toString();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(MainActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        }

        pd.dismiss();
    }
}
