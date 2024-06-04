package com.example.plant.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.ListHistory
import com.example.plant.R
import com.example.plant.databinding.FragmentHomeBinding
import com.example.plant.ui.history.HistoryAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding : FragmentHomeBinding? = null
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
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel.setHistory(setListHistories())

        homeViewModel.historyList.observe(viewLifecycleOwner) {
            showRecyclerList(it)
        }
        val root : View = binding.root

        return root
    }

    private fun setListHistories() : ArrayList<ListHistory>{
        val dataName = resources.getStringArray(R.array.name_disease)
        val dataDesc = resources.getStringArray(R.array.desc_disease)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataPercentage = resources.getStringArray(R.array.percentage)
        val dataTime = resources.getStringArray(R.array.date_history)
        val listHistory = ArrayList<ListHistory>()
        for(i in 1..2){
            val history = ListHistory(dataName[i], dataDesc[i], dataPercentage[i], dataTime[i], dataPhoto.getResourceId(i,-1))
            listHistory.add(history)
        }
        return listHistory
    }

    private fun showRecyclerList(list:ArrayList<ListHistory>){
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val listHistoryAdapter = HistoryAdapter()
        listHistoryAdapter.submitList(list)
        binding.recyclerView.adapter =listHistoryAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}