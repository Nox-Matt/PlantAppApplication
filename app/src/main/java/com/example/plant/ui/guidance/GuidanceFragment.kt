package com.example.plant.ui.guidance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.ListGuidance
import com.example.plant.ListHistory
import com.example.plant.R
import com.example.plant.databinding.FragmentGuidanceBinding
import com.example.plant.databinding.FragmentHistoryBinding
import com.example.plant.ui.history.HistoryAdapter
import com.example.plant.ui.network.response.DataGuide

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GuidanceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuidanceFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentGuidanceBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val guidanceViewModel = ViewModelProvider(this).get(GuidanceViewModel::class.java)
        _binding = FragmentGuidanceBinding.inflate(inflater, container, false)

        guidanceViewModel.getGuidanceList()

        guidanceViewModel.guidanceList.observe(viewLifecycleOwner){
            showRecyclerList(it)
        }

        val root : View = binding.root

        return root
    }

    private fun setListGuidance(): ArrayList<ListGuidance>{
        val dataTitle = resources.getStringArray(R.array.data_title_guidance)
        val dataTime = resources.getStringArray(R.array.date_history)
        val dataPhoto = resources.obtainTypedArray(R.array.data_guidance_photo)
        val listGuidance = ArrayList<ListGuidance>()
        for(i in dataTitle.indices){
            val guidance = ListGuidance(dataTitle[i], dataTime[i], dataPhoto.getResourceId(i,-1))
            listGuidance.add(guidance)
        }
        return listGuidance
    }

    private fun showRecyclerList(list: List<DataGuide?>?){
        binding.rvGuidance.layoutManager = LinearLayoutManager(requireContext())
        val listGuidanceAdapter = GuidanceAdapter()
        listGuidanceAdapter.submitList(list)
        binding.rvGuidance.adapter =listGuidanceAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GuidanceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GuidanceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}