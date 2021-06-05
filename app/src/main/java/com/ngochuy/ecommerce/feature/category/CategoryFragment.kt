package com.ngochuy.ecommerce.feature.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ngochuy.ecommerce.R
import com.ngochuy.ecommerce.data.Status
import com.ngochuy.ecommerce.di.Injection
import com.ngochuy.ecommerce.feature.category.adapter.CategoryAdapter
import com.ngochuy.ecommerce.viewmodel.CategoryViewModel
import com.ngochuy.ecommerce.widget.GridItemDecoration
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * A simple [Fragment] subclass.
 */
class CategoryFragment : Fragment() {

    private val categoryAdapter by lazy {
        CategoryAdapter { categoryID -> showProductsOfCategory(categoryID) }
    }
    private fun showProductsOfCategory(categoryID: Int) {
//        val intent = Intent(context, ProductsOfCategoryActivity::class.java)
//        intent.putExtra(CATEGORY_ID, categoryID)
//        requireActivity().startActivity(intent)
    }


    private lateinit var categoryViewModel: CategoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryViewModel = ViewModelProvider(
                this,
                Injection.provideCategoryViewModelFactory()
        )[CategoryViewModel::class.java]
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun bindViewModel() {
        categoryViewModel.listCategory.observe(viewLifecycleOwner, Observer {
            categoryAdapter.setListCategory(it)
        })
        categoryViewModel.networkStateCategory.observe(viewLifecycleOwner, Observer {
            progressCategory?.visibility =
                    if (it.status == Status.RUNNING) View.VISIBLE else View.GONE
        })
    }

    private fun initViews() {
        rvCategory.adapter = categoryAdapter
        rvCategory.setHasFixedSize(true)
        rvCategory.setItemViewCacheSize(20)
        rvCategory.layoutManager = GridLayoutManager(requireContext(), 3)
        rvCategory.addItemDecoration(GridItemDecoration(10, 3))
    }

}
