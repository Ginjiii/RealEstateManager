package com.openclassrooms.realestatemanager.ui.fragments

import android.os.Bundle
import android.view.*
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentLoanBinding
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.utils.Utils
import kotlin.math.pow


class LoanFragment : Fragment(R.layout.fragment_loan) {

    private var fragmentLoanBinding: FragmentLoanBinding? = null
    private val binding get() = fragmentLoanBinding!!

    private var totalPayment: Float = 0f
    private var annualPayment: Float = 0f
    private var totalInterest: Float = 0f
    private var monthlyPayment: Float = 0f
    private var nbrYear: Int = 0
    private var isDollar: Boolean = true
    private lateinit var menu: Menu


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentLoanBinding = FragmentLoanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        requireActivity().title = getString(R.string.loan_calculator)

        clearAllFields()

        binding.calculateButton.setOnClickListener {
            if (!Utils.validateInputFieldIfNullOrEmpty(binding.loanAmount, "Can't be empty")
                    or (!Utils.validateInputFieldIfNullOrEmpty(binding.interestRate, "Can't be empty"))
                    or (!Utils.validateInputFieldIfNullOrEmpty(binding.numberYears, "Can't be empty"))
            ) return@setOnClickListener

            loanPaymentCalculation()
            if (isDollar) displayResultsInDollar() else displayResultsInEuro()
        }
    }

    // Setup toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar, menu)
        menu.getItem(0).isVisible = true
        menu.getItem(1).isVisible = true
        menu.getItem(2).isVisible = false
        menu.getItem(3).isVisible = false
        menu.getItem(4).isVisible = false
        menu.getItem(5).isVisible = true
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tb_menu_currency -> {
                if (isDollar) {
                    menu.getItem(0).setIcon(R.drawable.dollar)
                    if (totalPayment == 0f) {
                        binding.loanAmount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.euro, 0, 0, 0)
                    } else {
                        convertResultsInEuro()
                    }
                    isDollar = !isDollar
                } else {
                    menu.getItem(0).setIcon(R.drawable.euro)
                    if (totalPayment == 0f) {
                        binding.loanAmount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dollar, 0, 0, 0)
                    } else {
                        convertResultsInDollar()
                    }
                    isDollar = !isDollar
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loanPaymentCalculation() {
        val loanAmount = binding.loanAmount.text.toString().toInt()
        val interestedRate = binding.interestRate.text.toString().toFloat()
        nbrYear = binding.numberYears.text.toString().toInt()

        val interestedRateYear = interestedRate / 100
        val interestedRateMonth = interestedRateYear / 12
        val nbrMonth = nbrYear * 12

        monthlyPayment = ((loanAmount * interestedRateMonth) * (1 + interestedRateMonth).pow(nbrMonth)) / ((1 + interestedRateMonth).pow(nbrMonth) - 1)

        totalPayment = monthlyPayment * nbrMonth
        totalInterest = totalPayment - loanAmount
        annualPayment = totalPayment / nbrYear
    }

    private fun convertResultsInDollar() {
        val loanAmountConverted = Utils.convertEuroToDollar(binding.loanAmount.text.toString().toInt())
        totalPayment = Utils.convertEuroToDollar(totalPayment.toInt()).toFloat()
        totalInterest = Utils.convertEuroToDollar(totalInterest.toInt()).toFloat()
        annualPayment = Utils.convertEuroToDollar(annualPayment.toInt()).toFloat()
        monthlyPayment = Utils.convertEuroToDollar(monthlyPayment.toInt()).toFloat()


        binding.apply {
            binding.loanAmount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dollar, 0, 0, 0)
            binding.loanAmount.setText(loanAmountConverted.toString())
            binding.totalPayment.text = resources.getString(R.string.total_amount)
            binding.totalPayment.text = resources.getString(R.string.total_amount)
            binding.totalInterest.text = resources.getString(R.string.total_interest)
            binding.annualPayment.text = resources.getString(R.string.annual_payment)
            binding.monthlyPayment.text = resources.getString(R.string.monthly_payment)
        }
    }

    private fun convertResultsInEuro() {
        val loanAmountConverted = Utils.convertDollarToEuro(binding.loanAmount.text.toString().toInt())
        totalPayment = Utils.convertDollarToEuro(totalPayment.toInt()).toFloat()
        totalInterest = Utils.convertDollarToEuro(totalInterest.toInt()).toFloat()
        annualPayment = Utils.convertDollarToEuro(annualPayment.toInt()).toFloat()
        monthlyPayment = Utils.convertDollarToEuro(monthlyPayment.toInt()).toFloat()

        binding.apply {
            binding.loanAmount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.euro, 0, 0, 0)
            binding.loanAmount.setText(loanAmountConverted.toString())
            binding.totalPayment.text = getString(R.string.total_amount)
            binding.totalInterest.text = resources.getString(R.string.total_interest)
            binding.annualPayment.text = resources.getString(R.string.annual_payment)
            binding.monthlyPayment.text = resources.getString(R.string.monthly_payment)
        }
    }

    private fun displayResultsInDollar() {
        binding.apply {
            binding.loanAmount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dollar, 0, 0, 0)
            binding.totalPayment.text = resources.getString(R.string.total_amount)
            binding.totalInterest.text = resources.getString(R.string.total_interest)
            binding.annualPayment.text = resources.getString(R.string.annual_payment)
            binding.monthlyPayment.text = resources.getString(R.string.monthly_payment)
        }
    }

    private fun displayResultsInEuro() {
        binding.apply {
            binding.loanAmount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.euro, 0, 0, 0)
            binding.totalPayment.text = resources.getString(R.string.total_amount)
            binding.totalInterest.text = resources.getString(R.string.total_interest)
            binding.annualPayment.text = resources.getString(R.string.annual_payment)
            binding.monthlyPayment.text = resources.getString(R.string.monthly_payment)
        }
    }

    private fun clearAllFields() {
        binding.apply {
            binding.loanAmount.text?.clear()
            binding.interestRate.text?.clear()
            binding.numberYears.text?.clear()
            binding.totalPayment.text = resources.getString(R.string.total_amount)
            binding.totalInterest.text = resources.getString(R.string.total_interest)
            binding.annualPayment.text = resources.getString(R.string.annual_payment)
            binding.monthlyPayment.text = resources.getString(R.string.monthly_payment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentLoanBinding = null
    }
}