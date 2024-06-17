package com.example.plant.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.MainActivity
import com.example.plant.R
import com.example.plant.ViewModelFactory
import com.example.plant.databinding.FragmentHomeBinding
import com.example.plant.getImageUri
import com.example.plant.pref.DataStoreViewModel
import com.example.plant.pref.UserPreference
import com.example.plant.pref.dataStore
import com.example.plant.ui.history.HistoryAdapter
import com.example.plant.ui.login.LoginActivity
import com.example.plant.ui.network.response.DataItem
import kotlinx.coroutines.launch
import java.io.File

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

    private lateinit var capturedTempImageUri: Uri

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        val root : View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val pref = UserPreference.getInstance(requireContext().applicationContext.dataStore)
        val datastoreViewModel = ViewModelProvider(this, ViewModelFactory(pref))[DataStoreViewModel::class.java]

        datastoreViewModel.getUserName().observe(viewLifecycleOwner) { username ->
            binding.greetingText1.text = "$username !"
        }

        datastoreViewModel.getTokenKey().observe(viewLifecycleOwner) {
            homeViewModel.getHistoryList(it)
            Log.d(TAG, "$it")
        }

        binding.txtSeeAll.setOnClickListener {
            findNavController().navigate(R.id.navigation_History)
            val bottomNavigationView = (activity as? MainActivity)?.navView
            bottomNavigationView?.selectedItemId = R.id.navigation_History
            true
        }


        homeViewModel.historyList.observe(viewLifecycleOwner) {
            if (it != null) {
                showRecyclerList(it)
            }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        homeViewModel.historyList.observe(viewLifecycleOwner) { historyList ->
            binding.emptyFormCondition.visibility = if (historyList.isNullOrEmpty()) View.VISIBLE else View.GONE
            binding.recyclerView.visibility = if (historyList.isNullOrEmpty()) View.GONE else View.VISIBLE

            (binding.recyclerView.adapter as? HistoryAdapter)?.submitList(historyList)
        }


        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                datastoreViewModel.setTokenKey("")
                datastoreViewModel.setValid(false)
            }
            val intentLogin = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intentLogin)
        }

        // Camera Activity
        val launcherIntentCamera = registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { isSuccess ->
            if (isSuccess) {
                val bundle = Bundle().apply { putParcelable("imageUri", capturedTempImageUri) }
                findNavController().navigate(R.id.navigation_Camera, bundle)
            }
        }

        binding.buttonToAnalyzed.setOnClickListener {
            capturedTempImageUri = getImageUri(requireContext())
            launcherIntentCamera.launch(capturedTempImageUri)
        }
    }



    private fun showRecyclerList(list:List<DataItem>){
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val listHistoryAdapter = HistoryAdapter(this,false)
        listHistoryAdapter.submitList(list)
        binding.recyclerView.adapter =listHistoryAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

        const val TAG = "HomeFragment"
    }
}