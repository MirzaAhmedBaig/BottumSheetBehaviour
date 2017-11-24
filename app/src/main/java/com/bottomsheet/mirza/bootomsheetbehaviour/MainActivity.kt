package com.bottomsheet.mirza.bootomsheetbehaviour

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<View>
    private var miniLayout: View? = null
    private var maxLayout: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        miniLayout = findViewById(R.id.miniLayout)
        maxLayout = findViewById(R.id.maxLayout)
        maxLayout!!.alpha = 0f


        bottom_sheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
        mBottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        mBottomSheetBehavior.isHideable = false
        mBottomSheetBehavior.skipCollapsed = false
        mBottomSheetBehavior.peekHeight = 200
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED


        mBottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    miniLayout!!.visibility = View.VISIBLE
                    mainLayout.alpha = 1f
                } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d(TAG, "Slide Offset :" + slideOffset)
                maxLayout!!.alpha = slideOffset
                mainLayout!!.alpha = (1 - slideOffset)
            }
        })

        miniLayout!!.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val params = miniLayout!!.layoutParams as RelativeLayout.LayoutParams
                mBottomSheetBehavior.peekHeight = params.height
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    miniLayout!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    miniLayout!!.viewTreeObserver.removeGlobalOnLayoutListener(this)
                }

            }
        }
        )
    }

    companion object {

        private val TAG = "MainActivity"
    }
}