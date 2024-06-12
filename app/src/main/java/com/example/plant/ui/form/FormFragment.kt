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
import com.example.plant.databinding.FragmentFormBinding
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

        // Adapter Initialization
        formAdapter = FormAdapter { form ->

            val intent = Intent(context, DetailFormActivity::class.java)
            intent.putExtra("form", form)
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
        }

        // Fetch the forum list
        formViewModel.getFormList()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
