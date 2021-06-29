package com.happytogether.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.happytogether.MainActivity
import com.happytogether.R
import com.happytogether.data.FirstRunSharePref
import com.happytogether.databinding.FragmentLoginBinding
import com.happytogether.utilities.FbController
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val EMAIL_TEST = "admin"
private const val PASSWORD_TEST = "12345"

public const val EXTRA_DATA = "12345"

class LoginFragment : Fragment() {

    lateinit var controller: FbController


    var myFirstRun : FirstRunSharePref? = null

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        controller = FbController(this.requireContext())
        myFirstRun = FirstRunSharePref(this.requireContext())
        var user = binding.eMail.text
        var pass = binding.password.text

        binding.loginBtn.setOnClickListener{
            Log.w("Check : ", "${controller.isExist(user.toString())} && ${controller.isExistPass(pass.toString())}")
            if (user.toString() == EMAIL_TEST && pass.toString() == PASSWORD_TEST) {
                myFirstRun!!.firstRun = false
                var intent = Intent(this.activity, MainActivity::class.java)
                startActivity(intent)
                this.requireActivity().finish()
            }
//            else if (controller.isExist(user.toString()) && controller.isExistPass(pass.toString())) {
//                if (controller.checkUser(user.toString(), pass.toString())) {
//                    myFirstRun!!.firstRun = false
//                    var intent = Intent(this.activity, MainActivity::class.java)
//                    intent.putExtra(EXTRA_DATA,user.toString())
//                    startActivity(intent)
//                    this.requireActivity().finish()
//                }
//            }
//            else if (controller.checkUser(user.toString(), pass.toString())) {
//                myFirstRun!!.firstRun = false
//                var intent = Intent(this.activity, MainActivity::class.java)
//                intent.putExtra(EXTRA_DATA,user.toString())
//                startActivity(intent)
//                this.requireActivity().finish()
//            }
            else {
                controller.bla()
                controller.isExist(user.toString())
//                (activity as LoginActivity).check(user.toString(), pass.toString())
                Toast.makeText(this.activity, "Login Failed", Toast.LENGTH_SHORT).show()
            }
            user.clear()
            if (pass != null) {
                pass.clear()
            }
        }

        binding.btnReg.setOnClickListener {
            val registerFragment = RegisterFragment.newInstance("a", "a")
            openFragment(registerFragment)
        }

        return view
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}