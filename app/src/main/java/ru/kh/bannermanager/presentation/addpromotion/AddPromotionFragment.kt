package ru.kh.bannermanager.presentation.addpromotion

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.Timestamp
import ru.kh.bannermanager.R
import ru.kh.bannermanager.data.collectionsrepo.PromotionsRepo
import ru.kh.bannermanager.databinding.FragmentAddPromotionBinding
import ru.kh.bannermanager.domain.appstate.AddPromotionAppState
import ru.kh.bannermanager.domain.entity.PromotionEntity
import java.util.*

class AddPromotionFragment : Fragment() {
    private var binding: FragmentAddPromotionBinding? = null
    private val _binding: FragmentAddPromotionBinding get() = binding!!
    private val viewModel: AddPromotionViewModel by lazy { AddPromotionViewModel(PromotionsRepo()) }
    private val observer: Observer<AddPromotionAppState> by lazy { Observer { render(it) } }



    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPromotionBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {
        createSpinner()
        _binding.tvDateStart.text = getCurrentDate()
        _binding.tvDateEnd.text = getCurrentDate()
        _binding.tvDateStart.setOnClickListener{setDateOfStart()}
        _binding.tvDateEnd.setOnClickListener{setDateOfEnd()}
        _binding.fabAddDocument.setOnClickListener {
            if (isValidDataForAdding())
                viewModel.addPromotion(getPromotionWithViewFields())
                    .observe(viewLifecycleOwner, observer)
        }

    }

    private fun createSpinner() {
        val spinner = _binding.spinner
        ArrayAdapter.createFromResource(requireContext(), R.array.language_array,
            android.R.layout.simple_spinner_item).also {adapter ->
                adapter.setDropDownViewResource(R.layout.dropdown_menu_popup_item)
            spinner.adapter = adapter

        }
    }

    private fun setDateOfStart(){
       val datePickerFragment = DatePickerFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.setFragmentResultListener("REQUEST_CODE",
            viewLifecycleOwner
        ) { resultKey, bundle ->
            if (resultKey == "REQUEST_CODE" && bundle.containsKey(resultKey))
                _binding.tvDateStart.text = bundle.getString(resultKey)
        }
        datePickerFragment.show(fragmentManager, "DATE_PICKER_DIALOG")
    }

    private fun setDateOfEnd(){
        val datePickerFragment = DatePickerFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.setFragmentResultListener("REQUEST_CODE",
            viewLifecycleOwner
        ) { resultKey, bundle ->
            if (resultKey == "REQUEST_CODE")
                _binding.tvDateEnd.text = bundle.getString(resultKey)
        }
        datePickerFragment.show(fragmentManager, "DATE_PICKER_DIALOG")
    }

    private fun getPromotionWithViewFields(): PromotionEntity {
        return PromotionEntity(
            _binding.editTextTitle.text.toString(),
            _binding.editTextDescription.text.toString(),
            _binding.editTextLinkForButton.text.toString(),
            _binding.editTextTextForButton.text.toString(),
            "",
            _binding.spinner.selectedItem.toString(),
            _binding.editTextLink.text.toString(),
            null, null,
            _binding.editTextTitle.text.toString()
        )
    }

    private fun getCurrentDate(): String {
        val timestamp = Timestamp.now()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("ru", "RU"))
        val netDate = Date(timestamp.seconds / 1000 )
        return sdf.format(netDate).toString()

    }

    private fun isValidDataForAdding(): Boolean =
        if (_binding.editTextTitle.text?.isEmpty() != true &&
            _binding.editTextDescription.text?.isEmpty() != true &&
            _binding.editTextLink.text?.isEmpty()!= true &&
            _binding.editTextLinkForButton.text?.isEmpty() != true &&
            _binding.editTextTextForButton.text?.isEmpty()!= true &&
            _binding.tvDateStart.text?.isEmpty() != true &&
            _binding.tvDateEnd.text?.isEmpty() != true
        ) {
            true
        } else {
            Toast.makeText(requireActivity(), "Fill in the fields", Toast.LENGTH_SHORT).show()
            false
        }

    private fun showProgressBar() {
        _binding.addPromotionProgressBar.visibility = View.VISIBLE
        _binding.addPromotionNestedScrollView.visibility = View.GONE
    }

    private fun hideProgressBar() {
        _binding.addPromotionProgressBar.visibility = View.GONE
        _binding.addPromotionNestedScrollView.visibility = View.VISIBLE
    }

    private fun showError(error: Throwable) {
        Toast.makeText(requireActivity(), error.toString(), Toast.LENGTH_SHORT).show()
    }


    private fun render(appState: AddPromotionAppState?) {
        when (appState) {
            is AddPromotionAppState.Loading -> showProgressBar()
            is AddPromotionAppState.Success -> {
                hideProgressBar()
            }
            is AddPromotionAppState.Error -> showError(appState.error)
        }
    }

}