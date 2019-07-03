package com.robert.trucksimtelemetry

import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.google.android.material.snackbar.Snackbar
import com.robert.trucksimtelemetry.dagger.main.inject
import com.robert.trucksimtelemetry.databinding.ActivityMainBinding
import com.robert.trucksimtelemetry.util.delegates.contentView
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private val binding by contentView<MainActivity, ActivityMainBinding>(
        R.layout.activity_main
    )

    @Inject
    lateinit var viewModel: MainViewModel

    private var connectErrorIsShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inject(this)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setSupportActionBar(binding.toolbar)

        binding.progressBar.hide()

        viewModel.uiModel.observe(this, Observer {
            val uiModel = it ?: return@Observer

            if (uiModel.truck != null && !uiModel.truck.consumed) {
                uiModel.truck.consume()?.let { truck ->
                    binding.truckInfoTextView.text = truck.getDisplayInfo()
                }
            }

            if (uiModel.showError != null && !uiModel.showError.consumed) {
                uiModel.showError.consume()?.let { errorStringRes ->
                    if (!connectErrorIsShown) {
                        Snackbar
                            .make(binding.container, errorStringRes, Snackbar.LENGTH_INDEFINITE)
                            .addCallback(object : Snackbar.Callback() {
                                override fun onShown(sb: Snackbar?) {
                                    super.onShown(sb)
                                    connectErrorIsShown = true
                                }

                                override fun onDismissed(
                                    transientBottomBar: Snackbar?,
                                    event: Int
                                ) {
                                    super.onDismissed(transientBottomBar, event)
                                    connectErrorIsShown = false
                                }
                            }).show()
                    }
                }
            }
        })

        binding.showGuideButton.setOnClickListener {
            viewModel.startRealtimeTelemetry()
        }

    }

    override fun onDestroy() {
        viewModel.realtimeTelemetryTimer?.cancel()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                val savedServerIP = viewModel.getServerIP()
                MaterialDialog(this).show {
                    icon(R.mipmap.ic_launcher_round)
                    title(text = "Enter Server IP")
                    input(
                        hint = "Server IP",
                        prefill = savedServerIP,
                        inputType = InputType.TYPE_CLASS_PHONE
                    ) { _, text ->
                        text.isNotEmpty().run {
                            viewModel.setServerIP(text.toString())
                            finish()
                            startActivity(intent)
                        }
                    }
                    cornerRadius(res = R.dimen.dialog_corner_radius)
                    lifecycleOwner(binding.lifecycleOwner)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
