package vir.ilnrv291008.volleyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.solver.widgets.ResolutionNode
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var volleyReques: RequestQueue? = null
    val stringLink = "https://www.le.ac.uk/oerresources/bdra/html/page_09.htm"
    val jsonLinq  = "https://mysafeinfo.com/api/data?list=englishmonarchs&format=json"
    val earthQuakeLink = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/1.0_hour.geojson"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        volleyReques = Volley.newRequestQueue(this)

        //getString(stringLink)

        //getJsonArray(jsonLinq)
        getJsonObject(earthQuakeLink)

    }

    fun getString(url: String) {
        val stringReq = StringRequest(Request.Method.GET, url,
                Response.Listener { response: String ->
                    try {
                        Log.d("Response", response)

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                },
                Response.ErrorListener { error: VolleyError? ->
                    try {
                        Log.d("Error: ", error.toString())

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                })

        volleyReques!!.add(stringReq)


    }

    fun getJsonObject(url: String) {
        val jsonObjectReq = JsonObjectRequest(Request.Method.GET,url,
            Response.Listener {
                response: JSONObject ->
                try {

                   // val metadataObject = response.getJSONObject("features")

                    //var title = metadataObject.getString("title")

                   // var type = response.getString("type")

                    val featuresArray=response.getJSONArray("features")
                    for (i in 0 until featuresArray.length()) {
                        val propertyObj = featuresArray.getJSONObject(i).getJSONObject("properties")
                        var place =propertyObj.getString("place")

                        //geom

                        val geometry = featuresArray.getJSONObject(i).getJSONObject("geometry")
                        var geometryObj = geometry.getJSONArray("coordinates")

                        for (h in 0 until geometryObj.length()) {
                            val firstCoord = geometryObj.getDouble(0)

                            Log.d("coord = " ,firstCoord.toString())
                        }


                    }



                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener{
                error :VolleyError ->
                try {
                    Log.d("Response", error.toString())


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            })

        volleyReques!!.add(jsonObjectReq)
    }

    fun getJsonArray(url: String) {

        val jsonArray = JsonArrayRequest(Request.Method.GET,url,
        Response.Listener {
            response: JSONArray ->
            try {
                Log.d("Response", response.toString())

                for(i in 0 until response.length())
                {
                    val obj = response.getJSONObject(i)
                    var title = obj.getString("Name")
                    Log.d("tag",title)


                }

            } catch (e: JSONException) {
                e.printStackTrace()
            } },
        Response.ErrorListener {
            error: VolleyError? ->
            try {
                Log.d("Error: ", error.toString())

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        })

    volleyReques?.add(jsonArray)
    }
}