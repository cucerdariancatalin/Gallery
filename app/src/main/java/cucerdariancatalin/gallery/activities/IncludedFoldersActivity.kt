package cucerdariancatalin.gallery.activities

import android.os.Bundle
import com.simplemobiletools.commons.extensions.beVisibleIf
import com.simplemobiletools.commons.extensions.getProperTextColor
import com.simplemobiletools.commons.helpers.NavigationIcon
import com.simplemobiletools.commons.interfaces.RefreshRecyclerViewListener
import cucerdariancatalin.gallery.R
import cucerdariancatalin.gallery.adapters.ManageFoldersAdapter
import cucerdariancatalin.gallery.extensions.config
import kotlinx.android.synthetic.main.activity_manage_folders.*

class IncludedFoldersActivity : SimpleActivity(), RefreshRecyclerViewListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_folders)
        updateFolders()
        setupOptionsMenu()
        manage_folders_toolbar.title = getString(R.string.include_folders)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar(manage_folders_toolbar, NavigationIcon.Arrow)
    }

    private fun updateFolders() {
        val folders = ArrayList<String>()
        config.includedFolders.mapTo(folders) { it }
        manage_folders_placeholder.apply {
            text = getString(R.string.included_activity_placeholder)
            beVisibleIf(folders.isEmpty())
            setTextColor(getProperTextColor())
        }

        val adapter = ManageFoldersAdapter(this, folders, false, this, manage_folders_list) {}
        manage_folders_list.adapter = adapter
    }

    private fun setupOptionsMenu() {
        manage_folders_toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add_folder -> addFolder()
                else -> return@setOnMenuItemClickListener false
            }
            return@setOnMenuItemClickListener true
        }
    }

    override fun refreshItems() {
        updateFolders()
    }

    private fun addFolder() {
        showAddIncludedFolderDialog {
            updateFolders()
        }
    }
}
