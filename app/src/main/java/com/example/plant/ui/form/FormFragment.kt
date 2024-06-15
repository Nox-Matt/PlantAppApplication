package com.example.plant.ui.form

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plant.R
import com.example.plant.ViewModelFactory
import com.example.plant.databinding.FragmentFormBinding
import com.example.plant.pref.DataStoreViewModel
import com.example.plant.pref.UserPreference
import com.example.plant.pref.dataStore
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FormFragment : Fragment() {

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var formAdapter: FormAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val formViewModel = ViewModelProvider(this)[FormViewModel::class.java]
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        val root: View = binding.root
        showLoading(true)

        // Adapter Initialization
        formAdapter = FormAdapter { form ->

            val intent = Intent(context, DetailFormActivity::class.java)
            intent.putExtra("form_id", form.id)
            intent.putExtra("form_title", form.title)
            intent.putExtra("form_description", form.question)
            intent.putExtra("form_username", form.username)
            startActivity(intent)
        }

        //FloatingActionButton Intent
        val buttonToAdd = binding.addQuestionFloating
        buttonToAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddFormActivity::class.java)
            startActivity(intent)
        }

        // RecyclerView setup
        binding.questionRecycler.apply {
            adapter = formAdapter
            layoutManager = LinearLayoutManager(context)
        }

        formViewModel.formList.observe(viewLifecycleOwner) { forumList ->
            formAdapter.submitList(forumList)
            showLoading(false)
        }

        // Fetch the forum list
        val pref = UserPreference.getInstance(requireContext().applicationContext.dataStore)
        val datastoreViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            DataStoreViewModel::class.java)

        datastoreViewModel.getTokenKey().observe(viewLifecycleOwner){
            formViewModel.getFormList(it)
        }

//        formViewModel.isLoading.observe(viewLifecycleOwner){
//            showLoading(it)
//        }

        return root
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
        _binding = null
    }
}
