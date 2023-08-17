package CSE.DeX.dexone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private  const val Tag = "MainActivity"
private  const val INITIAL_TIP_PERCENTAGE = 15
class MainActivity : AppCompatActivity() {
    private lateinit var etBaseAmount:TextView
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipPercentLabel: TextView
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private  lateinit var tipsThanking:TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etBaseAmount = findViewById(R.id.etBaseAmount)
        tipsThanking = findViewById(R.id.tipsThanking)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        seekBarTip.progress = INITIAL_TIP_PERCENTAGE;
        tvTipPercentLabel.text = "$INITIAL_TIP_PERCENTAGE%"
        handleMessage()

        seekBarTip.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(Tag, "onprogress: $p1")
                tvTipPercentLabel.text = "$p1%"
                computeTipAndTotal()
                handleMessage()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

        etBaseAmount.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                computeTipAndTotal();
            }

        })



    }

    private fun handleMessage(){
        var tips = tvTipPercentLabel.text.toString().replace('%',' ').toDouble();
        if( tips<=10){
            tipsThanking.text = "Thanks Sir"
        }else if(tips in 10.0..20.0){
            tipsThanking.text = "Amazing"
        }else{
            tipsThanking.text = "Thank you so much sir"
        }
    }

    private fun computeTipAndTotal() {
        if(etBaseAmount.text.isEmpty()){
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            return;
        }
       val baseAmount =  etBaseAmount.text.toString().toDouble()
        val tipPercentage = seekBarTip.progress
        val tipAmount = "%.2f".format(baseAmount*tipPercentage/100).toString().toDouble()
        val totalAmount = "%.2f".format(baseAmount+tipAmount)
        tvTipAmount.text = tipAmount.toString()
        tvTotalAmount.text = totalAmount.toString()

    }
}

