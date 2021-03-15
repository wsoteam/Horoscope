package com.wsoteam.horoscopes

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProviders
import com.amplitude.api.Amplitude
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.wsoteam.horoscopes.models.Sign
import com.wsoteam.horoscopes.notification.AlarmReceiver
import com.wsoteam.horoscopes.notification.EveningAlarmReceiver
import com.wsoteam.horoscopes.presentation.form.FormActivity
import com.wsoteam.horoscopes.presentation.lost.NetLostActivity
import com.wsoteam.horoscopes.presentation.main.CacheData
import com.wsoteam.horoscopes.presentation.main.ICachedData
import com.wsoteam.horoscopes.presentation.main.ILoadState
import com.wsoteam.horoscopes.presentation.main.MainVM
import com.wsoteam.horoscopes.presentation.premium.ab.DayCatPremiumActivity
import com.wsoteam.horoscopes.presentation.premium.onboarding.EnterActivity
import com.wsoteam.horoscopes.presentation.premium.onboarding.diamond.DiamondEnterActivity
import com.wsoteam.horoscopes.presentation.premium.onboarding.girl.GirlEnterActivity
import com.wsoteam.horoscopes.presentation.premium.onboarding.hair.HairEnterActivity
import com.wsoteam.horoscopes.presentation.premium.onboarding.smartphone.PhoneEnterActivity
import com.wsoteam.horoscopes.presentation.premium.onboarding.space.SpaceEnterActivity
import com.wsoteam.horoscopes.utils.PreferencesProvider
import com.wsoteam.horoscopes.utils.ads.AdCallbacks
import com.wsoteam.horoscopes.utils.ads.AdWorker
import com.wsoteam.horoscopes.utils.ads.BannerFrequency
import com.wsoteam.horoscopes.utils.ads.NativeProvider
import com.wsoteam.horoscopes.utils.analytics.Analytic
import com.wsoteam.horoscopes.utils.analytics.CustomTimer
import com.wsoteam.horoscopes.utils.analytics.ErrorInterceptor
import com.wsoteam.horoscopes.utils.analytics.FBAnalytic
import com.wsoteam.horoscopes.utils.analytics.experior.ETimer
import com.wsoteam.horoscopes.utils.analytics.experior.TagManager
import com.wsoteam.horoscopes.utils.choiceSign
import com.wsoteam.horoscopes.utils.fcm.FCMWork
import com.wsoteam.horoscopes.utils.loger.L
import com.wsoteam.horoscopes.utils.remote.ABConfig
import kotlinx.android.synthetic.main.stories_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity(R.layout.splash_activity) {

    var counter = 0
    var MAX = 4
    var isNextScreenLoading = false
    lateinit var vm: MainVM
    var isAdLoaded = false
    var isFirstSplash = false
    var isNetStateOk = true

    /*object branchListener : Branch.BranchReferralInitListener {
        override fun onInitFinished(referringParams: JSONObject?, error: BranchError?) {

            if (error == null) {

                Log.i("BRANCH SDK", referringParams.toString())
                // Retrieve deeplink keys from 'referringParams' and evaluate the values to determine where to route the user
                // Check '+clicked_branch_link' before deciding whether to use your Branch routing logic
            } else {
                Log.e("BRANCH SDK", error.message)
            }
        }
    }*/


    private fun postGoNext(i: Int, tag: String) {
        counter += i
        L.log("postGoNext -- $counter -- $tag")
        if (counter >= MAX) {
            if (!isNextScreenLoading) {
                isNextScreenLoading = true
                goNext()
            }
        }
    }


    private fun goNext() {
        L.log("goNext")
        var intent: Intent
        if (PreferencesProvider.getName() != "" && PreferencesProvider.getBirthday() != "") {
            intent = if (!PreferencesProvider.isShowCatPremium && isTimeHasPassed() && false) {
                Intent(this, DayCatPremiumActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java)
            }
            L.log("main activity enter")
        } else {
            if (!PreferencesProvider.isShowOnboard) {
                L.log(PreferencesProvider.getVersion()!!)
                PreferencesProvider.isShowOnboard = true
                intent = when (PreferencesProvider.getVersion()) {
                    ABConfig.A -> {
                        dropNewTheme()
                        Intent(this, DiamondEnterActivity::class.java)
                    }
                    ABConfig.B -> {
                        setNewTheme()
                        Intent(this, EnterActivity::class.java)
                    }
                    ABConfig.C -> {
                        setNewTheme()
                        Intent(this, SpaceEnterActivity::class.java)
                    }
                    ABConfig.D -> {
                        setNewTheme()
                        Intent(this, GirlEnterActivity::class.java)
                    }
                    ABConfig.E -> {
                        setNewTheme()
                        Intent(this, HairEnterActivity::class.java)
                    }
                    ABConfig.F -> {
                        setNewTheme()
                        Intent(this, PhoneEnterActivity::class.java)
                    }
                    ABConfig.G -> {
                        dropNewTheme()
                        Intent(this, DiamondEnterActivity::class.java)
                    }
                    ABConfig.H -> {
                        setNewTheme()
                        Intent(this, DiamondEnterActivity::class.java)
                    }
                    else -> {
                        setNewTheme()
                        Intent(this, EnterActivity::class.java)
                    }
                }
                L.log("Enter activity enter")
            } else {
                intent = Intent(this, FormActivity::class.java)
                L.log("formActivity enter")
            }
        }
        startActivity(intent)
        finish()

    }

    private fun isTimeHasPassed(): Boolean {
        return Calendar.getInstance().timeInMillis - PreferencesProvider.firstEnterTime > PreferencesProvider.FIRST_ENTER_TIME_TRIGGER
    }

    private fun getKey() {
        val info: PackageInfo
        try {
            info = packageManager.getPackageInfo(
                "com.wsoteam.horoscopes",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                var md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("LOL", something)
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e("name not found", e1.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("no such an algorithm", e.toString())
        } catch (e: Exception) {
            Log.e("exception", e.toString())
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FCMWork.getFCMToken()
        bindFirstOpenTime()
        if (!PreferencesProvider.isShowOnboard){
            Analytic.firstStart()
        }
        if (!PreferencesProvider.isSetuped) {
            CustomTimer.startFirstSplashTimer()
            ETimer.trackStart(ETimer.FIRST_SPLASH)
            AppEventsLogger
                .newLogger(this)
                .logEvent("fb_mobile_first_app_launch")
            FBAnalytic.logFirstLaunch(this)
            PreferencesProvider.isSetuped = true
            isFirstSplash = true
        } else {
            CustomTimer.startNextSplashTimer()
            ETimer.trackStart(ETimer.NEXT_SPLASH)
        }


        BannerFrequency.runSetup()

        var vm = ViewModelProviders
            .of(this)
            .get(MainVM::class.java)

        vm.preLoadData(object : ILoadState {
            override fun throwError(message: String) {
                isNetStateOk = false
                ErrorInterceptor.throwError(message)
                startActivity(Intent(this@SplashActivity, NetLostActivity::class.java))
                finishAffinity()
            }
        })


        if (PreferencesProvider.getBirthday() != "") {
            CacheData.setObserver(object : ICachedData {
                override fun cachedDataReady() {
                    makeCurrentScreen(CacheData.getCachedData()!!)
                    CacheData.removeObservers()
                }
            })
        }

        bindRetention()
        Analytic.start()
        PreferencesProvider.setBeforePremium(Analytic.start_premium)
        NativeProvider.loadNative()
        bindTest()
        //refreshNotifications()


        AdWorker.init(this)
        AdWorker.isNeedShowInter =
            PreferencesProvider.getName() != "" && PreferencesProvider.getBirthday() != ""
        AdWorker.adCallbacks = object : AdCallbacks {
            override fun onAdClosed() {
                postGoNext(2, "onAdClosed")
                AdWorker.unSubscribe()
            }

            override fun onAdLoaded() {
                MAX++
                isAdLoaded = true
            }
        }
        try {
            trackUser()
        } catch (ex: Exception) {
            L.log("crash")
            Analytic.crashAttr()
        }


        CoroutineScope(Dispatchers.IO).launch {
            TimeUnit.SECONDS.sleep(4)
            if (isAdLoaded) {
                postGoNext(1, "sleep4")
            } else {
                postGoNext(2, "sleep4")
            }
            determineCountry()
            if (isFirstSplash) {
                CustomTimer.stopFirstSplashTimer()
                ETimer.trackEnd(ETimer.FIRST_SPLASH)
            } else {
                CustomTimer.stopNextSplashTimer()
                ETimer.trackEnd(ETimer.NEXT_SPLASH)
            }
        }
        if (intent.getStringExtra(Config.OPEN_FROM_NOTIFY) != null) {
            when (intent.getStringExtra(Config.OPEN_FROM_NOTIFY)) {
                Config.OPEN_FROM_NOTIFY -> {
                    Analytic.openFromNotif()
                    PreferencesProvider.setLastDayNotification(PreferencesProvider.DEF_TODAY_NOTIF)
                }
                Config.OPEN_FROM_EVENING_NOTIF -> Analytic.openFromEveningNotif()
            }
        }
    }

    /*override fun onStart() {
        super.onStart()
        // Branch init
        Branch.sessionBuilder(this).withCallback(branchListener).withData(this.intent?.data).init()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
        // Branch reinit (in case Activity is already in foreground when Branch link is clicked)
        Branch.sessionBuilder(this).withCallback(branchListener).reInit()
    }*/


    private fun bindFirstOpenTime() {
        if (PreferencesProvider.firstEnterTime == -1L) {
            PreferencesProvider.firstEnterTime = Calendar.getInstance().timeInMillis
        }
    }

    private fun determineCountry() {
        var country = resources.configuration.locale.country
        TagManager.setCountry(country)
    }

    private fun makeCurrentScreen(it: List<Sign>) {
        val index = choiceSign(PreferencesProvider.getBirthday()!!)
        tvTopTitleStories.text = resources.getStringArray(R.array.names_signs)[index]
        ivSignStories.setImageResource(
            resources.obtainTypedArray(R.array.imgs_signs)
                .getResourceId(index, -1)
        )
        tvTitleStories.text = "${getString(R.string.my_horoscope_on)} ${Calendar.getInstance()
            .get(Calendar.DAY_OF_MONTH)} ${Calendar.getInstance()
            .getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US)}"
        tvTextStories.setText(getCutText(it[index].today.text))

        CoroutineScope(Dispatchers.IO).launch {
            TimeUnit.SECONDS.sleep(2)
            //makeImage()
        }
    }

    /*private fun makeImage() {
        var view = findViewById<View>(R.id.llParentLayout)
        view.isDrawingCacheEnabled = true
        view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        view.buildDrawingCache()
        val uri = saveImage(view.drawingCache, applicationContext)
        view.isDrawingCacheEnabled = false

        PreferencesProvider.screenURI = uri.toString()
        postGoNext(1, "screenURI")
    }*/

    private fun getCutText(text: String): String {
        var array = text.split(".")
        var cutString = ""
        for (i in 0..1) {
            cutString = "$cutString${array[i]}. "
        }
        return cutString
    }

    private fun saveImage(bitmap: Bitmap, context: Context): Uri? {
        var imagesFolder = File(context.cacheDir, "images")
        var uri: Uri? = null

        try {
            imagesFolder.mkdirs()
            var file = File(imagesFolder, "shared_image.png")

            var outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
            outputStream.flush()
            outputStream.close()
            uri = FileProvider.getUriForFile(context, "com.mydomain.fileprovider", file)
        } catch (ex: Exception) {
            L.log("save error")
        }
        return uri
    }

    private fun bindRetention() {
        var currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        if (PreferencesProvider.firstEnter == -1) {
            PreferencesProvider.firstEnter = currentDay
        } else {
            when (currentDay - PreferencesProvider.firstEnter) {
                2 -> FBAnalytic.logTwoDays(this)
                7 -> FBAnalytic.logSevenDays(this)
            }
        }
    }


    private fun bindTest() {
        val firebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        firebaseRemoteConfig.setDefaultsAsync(R.xml.default_config)

        firebaseRemoteConfig.fetch(3600).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseRemoteConfig.activate()
                Amplitude.getInstance().logEvent("norm_ab")
            } else {
                Amplitude.getInstance().logEvent("crash_ab")
            }
            setABTestConfig(
                firebaseRemoteConfig.getString(ABConfig.REQUEST_STRING),
                firebaseRemoteConfig.getLong(ABConfig.REQUEST_STRING_PRICE).toInt()
            )
        }
    }

    private fun setABTestConfig(version: String, priceIndex: Int) {
        L.log("set test")
        L.log("$priceIndex")
        PreferencesProvider.setVersion(version)
        PreferencesProvider.priceIndex = priceIndex
        Analytic.setABVersion(version, priceIndex)
        Analytic.setVersion()
        if (!PreferencesProvider.isShowOnboard) {
            Analytic.firstSetVer()
        }
        postGoNext(1, "setABTestConfig")
    }

    private fun refreshNotifications() {
        if (PreferencesProvider.getNotifTime() == PreferencesProvider.DEFAULT_TIME_NOTIFY && PreferencesProvider.getNotifStatus()) {
            AlarmReceiver.startNotification(this, Config.DEF_HOUR_NOTIF, Config.DEF_MIN_NOTIF)
            EveningAlarmReceiver.startNotification(
                this,
                Config.DEF_HOUR_EVENING_NOTIF,
                Config.DEF_MIN_EVENING_NOTIF
            )
            L.log("refreshNotifications")
        } else if (PreferencesProvider.getNotifTime() != PreferencesProvider.DEFAULT_TIME_NOTIFY && PreferencesProvider.getNotifStatus()) {
            val (hours, minutes) = PreferencesProvider.getNotifTime().split(":").map { it.toInt() }
            L.log("$hours $minutes refresh")
            AlarmReceiver.startNotification(this, hours, minutes)
            EveningAlarmReceiver.startNotification(
                this,
                Config.DEF_HOUR_EVENING_NOTIF,
                Config.DEF_MIN_EVENING_NOTIF
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AdWorker.unSubscribe()
    }

    private fun trackUser() {
        var client = InstallReferrerClient.newBuilder(this).build()
        client.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> sendAnal(client.installReferrer.installReferrer)
                    InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR -> sendAnal("DEVELOPER_ERROR")
                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> sendAnal(
                        "FEATURE_NOT_SUPPORTED"
                    )
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED -> sendAnal("SERVICE_DISCONNECTED")
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> sendAnal("SERVICE_UNAVAILABLE")
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                sendAnal("onInstallReferrerServiceDisconnected")
            }
        })
    }

    private fun sendAnal(s: String) {
        val clickId = getClickId(s)
        var mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        var bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CAMPAIGN, clickId)
        bundle.putString(FirebaseAnalytics.Param.MEDIUM, clickId)
        bundle.putString(FirebaseAnalytics.Param.SOURCE, clickId)
        bundle.putString(FirebaseAnalytics.Param.ACLID, clickId)
        bundle.putString(FirebaseAnalytics.Param.CONTENT, clickId)
        bundle.putString(FirebaseAnalytics.Param.CP1, clickId)
        bundle.putString(FirebaseAnalytics.Param.VALUE, clickId)
        mFirebaseAnalytics.logEvent("traffic_id", bundle)
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle)
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.CAMPAIGN_DETAILS, bundle)

        postGoNext(1, "sendAnal")
    }

    private fun getClickId(s: String): String {
        var id = s
        if (s.contains("&")) {
            id = s.split("&")[0]
        }
        return id
    }

    private fun setNewTheme() {
        PreferencesProvider.isNeedNewTheme = true
    }

    private fun dropNewTheme() {
        PreferencesProvider.isNeedNewTheme = false
    }
}
