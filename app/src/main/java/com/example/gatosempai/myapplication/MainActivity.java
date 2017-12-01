package com.example.gatosempai.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.gatosempai.myapplication.common.data.database.model.InvestmentTermDB;
import com.example.gatosempai.myapplication.common.data.database.model.InvestmentTermDBImpl;
import com.example.gatosempai.myapplication.common.data.database.model.InvestmentTermFolios;
import com.example.gatosempai.myapplication.common.data.database.model.LoginDB;
import com.example.gatosempai.myapplication.common.data.database.model.LoginDBImpl;
import com.example.gatosempai.myapplication.common.data.database.model.LoginInstalledData;
import com.example.gatosempai.myapplication.common.data.database.model.OtraDB;
import com.example.gatosempai.myapplication.common.data.database.model.OtraDBImpl;
import com.example.gatosempai.myapplication.common.data.database.model.OtraModel;
import com.example.gatosempai.myapplication.common.data.database.model.PruebaDB;
import com.example.gatosempai.myapplication.common.data.database.model.PruebaDBImpl;
import com.example.gatosempai.myapplication.common.data.database.model.PruebaModel;
import com.example.gatosempai.myapplication.common.data.database.model.SecondDB;
import com.example.gatosempai.myapplication.common.data.database.model.SecondDBImpl;
import com.example.gatosempai.myapplication.common.data.database.model.SecondModel;
import com.example.gatosempai.myapplication.common.data.database.realm.RealmProvider;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.Set;

import io.realm.Realm;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmProvider.configureRealm(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            SecondModel second = new SecondModel();
            second.setName("22222222");
            saveSecond(second);

            //readSecond();
        } else if (id == R.id.nav_gallery) {


        } else if (id == R.id.nav_slideshow) {
            //LoginInstalledData login = new LoginInstalledData();
            //login.setUserName("Prueba");
            //login.setUserNumber("123456");
            //saveLogin(login);

            readLogin();

        } else if (id == R.id.nav_manage) {
            //InvestmentTermFolios term = new InvestmentTermFolios();
            //term.setsInvestmentDueDate("ya fue");
            //saveInvestment(term);

            readInvestment();

        } else if (id == R.id.nav_share) {
            //OtraModel otra = new OtraModel();
            //otra.setName("efewfvrglbmronkvmcslaxnfvlkmc");
            //saveOtra(otra);

            readOtra();

            //RealmProvider.initEcc();
        } else if (id == R.id.nav_send) {
            //PruebaModel pruebas = new PruebaModel();
            //pruebas.setName("1654165561");
            //savePrueba(pruebas);

            readPrueba();

            //listAlgorithms();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void  listAlgorithms() {
        Provider[] providers = Security.getProviders();
        for (Provider p : providers) {
            System.out.printf("ORP :%s/%s/\n", p.getName(), p.getInfo());
            Set<Service> services = p.getServices();
            for (Service s : services) {
                System.out.printf("ORP: \t%s/%s\n", s.getType(),
                        s.getAlgorithm());
            }
        }
    }

    private void readLogin() {
        final Realm realm = RealmProvider.getInstance();
        final LoginDB db = new LoginDBImpl(realm);
        db.getLoginData(1L)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginInstalledData>() {
                    @Override
                    public void onCompleted() {
                        //unused
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ORP",  e.getMessage());
                    }

                    @Override
                    public void onNext(LoginInstalledData dataSaved) {
                        System.out.println("ORP readLogin name: "+dataSaved.getUserName());
                        System.out.println("ORP readLogin number: "+dataSaved.getUserNumber());
                    }
                });
    }

    private void saveLogin(LoginInstalledData login) {
        final Realm realm = RealmProvider.getInstance();
        final LoginDB db = new LoginDBImpl(realm);
        login.setId(1L);
        db.addLoginData(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginInstalledData>() {
                    @Override
                    public void onCompleted() {
                        //unused
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ORP", e.getMessage());
                    }

                    @Override
                    public void onNext(LoginInstalledData investmentTermFolios) {
                        System.out.println("ORP saveLogin");
                    }
                });
    }

    private void readInvestment() {
        final Realm realm = RealmProvider.getInstance();
        final InvestmentTermDB db = new InvestmentTermDBImpl(realm);
        db.getInvestmentTerm(2L)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InvestmentTermFolios>() {
                    @Override
                    public void onCompleted() {
                        //unused
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ORP",  e.getMessage());
                    }

                    @Override
                    public void onNext(InvestmentTermFolios dataSaved) {
                        System.out.println("ORP readInvestment name: "+dataSaved.getsInvestmentDueDate());
                    }
                });
    }

    private void saveInvestment(InvestmentTermFolios term) {
        final Realm realm = RealmProvider.getInstance();
        final InvestmentTermDB db = new InvestmentTermDBImpl(realm);
        term.setId(2L);
        db.addInvestmentTerm(term)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InvestmentTermFolios>() {
                    @Override
                    public void onCompleted() {
                        //unused
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ORP", e.getMessage());
                    }

                    @Override
                    public void onNext(InvestmentTermFolios investmentTermFolios) {
                        System.out.println("ORP saveInvestment");
                    }
                });
    }

    private void readOtra() {
        final Realm realm = RealmProvider.getInstance();
        final OtraDB db = new OtraDBImpl(realm);
        db.getOtraData(3L)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OtraModel>() {
                    @Override
                    public void onCompleted() {
                        //unused
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ORP",  e.getMessage());
                    }

                    @Override
                    public void onNext(OtraModel dataSaved) {
                        System.out.println("ORP readOtra name: "+dataSaved.getName());
                    }
                });
    }

    private void saveOtra(OtraModel otra) {
        final Realm realm = RealmProvider.getInstance();
        final OtraDB db = new OtraDBImpl(realm);
        otra.setId(3L);
        db.addOtraData(otra)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OtraModel>() {
                    @Override
                    public void onCompleted() {
                        //unused
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ORP", e.getMessage());
                    }

                    @Override
                    public void onNext(OtraModel data) {
                        System.out.println("ORP saveOtra");
                    }
                });
    }

    private void readPrueba() {
        final Realm realm = RealmProvider.getInstance();
        final PruebaDB db = new PruebaDBImpl(realm);
        db.getPruebaData(4L)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PruebaModel>() {
                    @Override
                    public void onCompleted() {
                        //unused
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ORP",  e.getMessage());
                    }

                    @Override
                    public void onNext(PruebaModel dataSaved) {
                        System.out.println("ORP readPrueba name: "+dataSaved.getName());
                    }
                });
    }

    private void savePrueba(PruebaModel otra) {
        final Realm realm = RealmProvider.getInstance();
        final PruebaDB db = new PruebaDBImpl(realm);
        otra.setId(4L);
        db.addPruebaData(otra)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PruebaModel>() {
                    @Override
                    public void onCompleted() {
                        //unused
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ORP", e.getMessage());
                    }

                    @Override
                    public void onNext(PruebaModel data) {
                        System.out.println("ORP savePrueba");
                    }
                });
    }

    private void readSecond() {
        final Realm realm = RealmProvider.getInstance();
        final SecondDB db = new SecondDBImpl(realm);
        db.getSecondData(5L)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SecondModel>() {
                    @Override
                    public void onCompleted() {
                        //unused
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ORP",  e.getMessage());
                    }

                    @Override
                    public void onNext(SecondModel dataSaved) {
                        System.out.println("ORP readSecond name: "+dataSaved.getName());
                    }
                });
    }

    private void saveSecond(SecondModel otra) {
        final Realm realm = RealmProvider.getInstance();
        final SecondDB db = new SecondDBImpl(realm);
        otra.setId(5L);
        db.addSecondData(otra)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SecondModel>() {
                    @Override
                    public void onCompleted() {
                        //unused
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ORP", e.getMessage());
                    }

                    @Override
                    public void onNext(SecondModel data) {
                        System.out.println("ORP saveSecond");
                    }
                });
    }
}
