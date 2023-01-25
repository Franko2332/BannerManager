package ru.kh.bannermanager.presentation


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.kh.bannermanager.R
import ru.kh.bannermanager.databinding.ActivityMainBinding
import ru.kh.bannermanager.presentation.addpromotion.AddPromotionFragment
import ru.kh.bannermanager.presentation.addpromotion.ShowAddPromotionFragmentListener
import ru.kh.bannermanager.presentation.promotions.PromotionsFragment


class MainActivity : AppCompatActivity(), ShowAddPromotionFragmentListener {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val fragmentsMap: HashMap<String, Fragment> = HashMap()
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        fragmentsMap[PROMOTIONS_FRAGMENT_NAME] = PromotionsFragment()
        fragmentManager = supportFragmentManager
        if (fragmentManager.findFragmentById(R.id.container) !is AddPromotionFragment) {
            fragmentsMap.get(PROMOTIONS_FRAGMENT_NAME)?.let {
                fragmentManager.beginTransaction().add(R.id.container, it, PROMOTIONS_FRAGMENT_NAME)
                    .commit()
            }
        }
    }

    override fun showAddPromotionFragment() {
        fragmentManager.beginTransaction().replace(R.id.container, AddPromotionFragment(), null)
            .addToBackStack(
                ADD_PROMOTION_FRAGMENT_NAME
            ).commit()
    }

    companion object {
        private const val PROMOTIONS_FRAGMENT_NAME = "PROMOTION_FRAGMENT"
        private const val ADD_PROMOTION_FRAGMENT_NAME = "ADD_PROMOTION_FRAGMENT"
    }
}
