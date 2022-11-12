import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.gl4.tp2.R
import java.util.*
import kotlin.collections.ArrayList


class studentListAdapter(private val dataSet: ArrayList<Student>,filterPresence :String) : Filterable,
    RecyclerView.Adapter<studentListAdapter.ViewHolder>()
    {
        var dataFilterList = ArrayList<Student>()
        init {
            // associer le tableau des données initiales
            dataFilterList = dataSet
            Log.d("filter",filterPresence)
            if(filterPresence != ""){
                if (filterPresence == "Absent(e)")
                    dataFilterList = dataFilterList.filter { student: Student -> !student.etat }
                            as ArrayList<Student>
                else
                    dataFilterList = dataFilterList.filter { student: Student -> student.etat }
                            as ArrayList<Student>
            }
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView
            val imageView: ImageView
            val checkBox: CheckBox

            init {
                textView = view.findViewById(R.id.textView)
                imageView = view.findViewById(R.id.imageView)
                checkBox = view.findViewById(R.id.checkBox)
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.student_item, viewGroup, false)

            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            viewHolder.textView.text =
                dataFilterList[position].nom + " " + dataFilterList[position].prenom
            if (dataFilterList[position].getGender() == "F")
                viewHolder.imageView.setImageResource(R.drawable.girl)
            else
                viewHolder.imageView.setImageResource(R.drawable.boy)

            viewHolder.checkBox.isChecked = dataFilterList[position].etat
            if (viewHolder.checkBox.isChecked) {
                viewHolder.checkBox.text = "Présent(e)"
                viewHolder.checkBox.setTextColor(R.color.purple_500)
            } else {
                viewHolder.checkBox.text = "Absent(e)"
                viewHolder.checkBox.setTextColor(Color.GRAY)
            }

            viewHolder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                dataFilterList[position].ChangerEtat(viewHolder.checkBox.isChecked)

                if (isChecked) {
                    viewHolder.checkBox.text = "Présent(e)"
                    viewHolder.checkBox.setTextColor(R.color.purple_500)

                } else {
                    viewHolder.checkBox.text = "Absent(e)"
                    viewHolder.checkBox.setTextColor(Color.GRAY)
                }
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = dataFilterList.size

        override fun getFilter(): Filter {
            return object : Filter(){
                override fun performFiltering(constraint: CharSequence?): FilterResults {
                    val charSearch = constraint.toString()
                    Log.d("filter", charSearch)
                    if (charSearch.isEmpty()) {
                        dataFilterList = dataSet
                    } else {
                        val resultList = ArrayList<Student>()
                        for (student in dataSet) {
                            if (student.nom.lowercase(Locale.ROOT)
                                    .contains(charSearch.lowercase(Locale.ROOT))
                            ) {
                                resultList.add(student)
                                Log.d("filter", student.nom+" "+student.prenom)
                            }
                        }
                        dataFilterList = resultList
                    }
                    val filterResults = FilterResults()
                    filterResults.values = dataFilterList
                    return filterResults
                }

                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    dataFilterList = results?.values as ArrayList<Student>
                    notifyDataSetChanged()
                }
        }
    }
}
