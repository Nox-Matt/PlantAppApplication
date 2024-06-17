package com.example.plant.ui.history

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.ListHistory
import com.example.plant.R
import com.example.plant.ViewModelFactory
import com.example.plant.databinding.FragmentHistoryBinding
import com.example.plant.pref.DataStoreViewModel
import com.example.plant.pref.UserPreference
import com.example.plant.pref.dataStore
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.Data
import com.example.plant.ui.network.response.DataItem
import com.example.plant.ui.network.response.HistoriesResponse
import io.github.muddz.styleabletoast.StyleableToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class
HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val lifecycleObserver = object : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            dialog?.dismiss()
        }
    }

    private var dialog: Dialog? = null

    private var _binding: FragmentHistoryBinding? = null
    private lateinit var deleteViewModel : DeleteViewModel

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                dialog?.dismiss()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        val deleteViewModel = ViewModelProvider(this).get(DeleteViewModel::class.java)
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val pref = UserPreference.getInstance(requireContext().applicationContext.dataStore)
        val datastoreViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            DataStoreViewModel::class.java)

        datastoreViewModel.getTokenKey().observe(viewLifecycleOwner){
            historyViewModel.getHistoryList(it)
        }



        historyViewModel.isLoading.observe(viewLifecycleOwner){
            val item = binding.rvHistory.adapter?.itemCount
            if(item == null){
                showLoading(it)
            }
        }

        deleteViewModel.isClickId.observe(viewLifecycleOwner){
            if(it){
                showDialog(deleteViewModel)
                deleteViewModel.dialoResult.observe(viewLifecycleOwner){dial->
                    if(dial){
                        datastoreViewModel.getTokenKey().observe(viewLifecycleOwner){token->
                            deleteViewModel.idDelete.observe(viewLifecycleOwner){idDel->
                                deleteViewModel.deleteHisotrybyId(token, idDel)
                            }
                        }
                        deleteViewModel.setDialogResult(false)
                    }
                }
            }

        }

        binding.btnClear.setOnClickListener {
            showDialog(deleteViewModel)
            deleteViewModel.dialoResult.observe(viewLifecycleOwner){dial->
                if(dial){
                    datastoreViewModel.getTokenKey().observe(viewLifecycleOwner){token->
                        deleteViewModel.deleteAllHistory(token )
                    }
                    deleteViewModel.setDialogResult(false)
                }
            }
        }

        deleteViewModel.isSuccess.observe(viewLifecycleOwner){
            if(it){
                StyleableToast.makeText(requireContext(), "Success to delete history", R.style.exampleToast).show()
                datastoreViewModel.getTokenKey().observe(viewLifecycleOwner){token->
                    historyViewModel.getHistoryList(token)
                }
            }else{
                StyleableToast.makeText(requireContext(), "Failed to delete", R.style.errorToast).show()
            }
        }

        historyViewModel.historyList.observe(viewLifecycleOwner) {
            if (it != null) {
                showRecyclerList(it)
            }else{
                val listnull : List<DataItem>? = null
                showRecyclerList(listnull)
            }
        }

        deleteViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }


        val root : View = binding.root



        return root
    }


    private fun showRecyclerList(list:List<DataItem>?){
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        val listHistoryAdapter = HistoryAdapter(this, true)
        listHistoryAdapter.submitList(list)
        binding.rvHistory.adapter =listHistoryAdapter
    }

    private fun showDialog(delViewModel: DeleteViewModel){
        if (!isAdded || activity == null || isRemoving || isDetached) {
            return
        }

        val dialog = Dialog(requireContext()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialogconfirm)
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        val btnYes : Button =dialog!!.findViewById(R.id.btn_yes)
        val btnNo: Button = dialog!!.findViewById(R.id.btn_No)


        btnYes.setOnClickListener {
            delViewModel.setIsClickId(false)
            delViewModel.setDialogResult(true)
            dialog!!.dismiss()
        }

        btnNo.setOnClickListener {
            delViewModel.setIsClickId(false)
            delViewModel.setDialogResult(false)
            dialog!!.dismiss()
        }
        dialog!!.show()
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(lifecycleObserver)
        dialog?.dismiss()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        const val TAG = "HistoryFragmet"
    }
}


