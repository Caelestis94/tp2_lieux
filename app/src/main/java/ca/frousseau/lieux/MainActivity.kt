package ca.frousseau.lieux

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ca.frousseau.lieux.data.LieuDatabase
import ca.frousseau.lieux.databinding.ActivityMainBinding
import ca.frousseau.lieux.model.Lieu
import ca.frousseau.lieux.ui.lieux.LieuxViewModel
import ca.frousseau.lieux.ui.lieuxVisites.LieuxVisitesViewModel
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navView: BottomNavigationView = binding.navView
        // populate action bar with menu

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.fragment_parametres)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * Fonction qui permet de mettre ?? jour un lieu dans la base de donn??es
     * ou de l'ajouter s'il est nouveau
     * @param lieu Le lieu ?? mettre ?? jour
     * @param new Si le lieu est nouveau ou non
     */
    fun updateLieu(lieu: Lieu, new: Boolean) {
        var lieuxViewModel = ViewModelProvider(this).get(LieuxViewModel::class.java)
        thread {
            if (new) {
                lieuxViewModel.insertLieu(lieu)
            } else {
                lieuxViewModel.updateLieu(lieu)
            }
        }
        if (new) {
            Toast.makeText(this, "Lieu ajout??", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Lieu modifi??", Toast.LENGTH_SHORT).show()
        }
    }

}