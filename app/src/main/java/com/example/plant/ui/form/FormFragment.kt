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
import com.example.plant.databinding.FragmentFormBinding

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

        // Adapter Initialization
        formAdapter = FormAdapter { form ->
            // Launch DetailFormActivity with data from clicked item
            val intent = Intent(context, DetailFormFragment::class.java)
            intent.putExtra("form", form)
            startActivity(intent)
        }

        // RecyclerView setup
        binding.recyclerView.apply {
            adapter = formAdapter
            layoutManager = LinearLayoutManager(context)
        }

        // Observing LiveData
        formViewModel.formList.observe(viewLifecycleOwner, Observer { formList ->
            formAdapter.submitList(formList)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}