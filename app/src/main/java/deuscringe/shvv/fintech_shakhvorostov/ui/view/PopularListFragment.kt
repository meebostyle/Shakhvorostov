package deuscringe.shvv.fintech_shakhvorostov.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import deuscringe.shvv.fintech_shakhvorostov.databinding.FragmentPopularListBinding
import deuscringe.shvv.fintech_shakhvorostov.ui.view.adapters.RecyclerAdapter

class PopularListFragment: Fragment() {
    private val recyclerAdapter = RecyclerAdapter()
    private var _binding: FragmentPopularListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}