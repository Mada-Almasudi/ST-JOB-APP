package com.example.students_job_app.student.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.students_job_app.R;
import com.example.students_job_app.student.OnApplyButtonClickedListener;
import com.example.students_job_app.utils.SharedPrefManager;
import com.example.students_job_app.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class JobDetailsFragment extends Fragment {

    Context context;
    TextView mPosition, mCompany, mAdvertiser, mDetails, mJobLocation, mRequiredSkills, mDate;
    String jobId, position, company, advertiserName, details, location, requiredSkills, date;
    Button mApplyBtn;
    public NavController navController;
    ProgressDialog pDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public JobDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            jobId = getArguments().getString("job_id");
            position = getArguments().getString("position");
            company = getArguments().getString("company");
            advertiserName = getArguments().getString("advertiser_name");
            details = getArguments().getString("details");
            location = getArguments().getString("location");
            requiredSkills = getArguments().getString("required_skills");
            date = getArguments().getString("date");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_job_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPosition = view.findViewById(R.id.position);
        mCompany = view.findViewById(R.id.company_name);
        mAdvertiser = view.findViewById(R.id.advertiser);
        mDetails = view.findViewById(R.id.details);
        mRequiredSkills = view.findViewById(R.id.required_skills);
        mJobLocation = view.findViewById(R.id.job_location);
        mDate = view.findViewById(R.id.date);
        mApplyBtn = view.findViewById(R.id.apply);
        mPosition.setText(position);
        mCompany.setText(company);
        mAdvertiser.setText(advertiserName);
        mDetails.setText(details);
        mRequiredSkills.setText(requiredSkills);
        mJobLocation.setText(location);
        mDate.setText(date);

        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setMessage("Processing Please wait...");
        navController = Navigation.findNavController(view);

        mApplyBtn.setOnClickListener(v -> {

            mApplyBtn.setEnabled(false);
            LayoutInflater factory = LayoutInflater.from(context);
            final View view1 = factory.inflate(R.layout.dialog_confirm_application, null);
            final AlertDialog dialog = new AlertDialog.Builder(context).create();
            dialog.setView(view1);
            dialog.setCanceledOnTouchOutside(true);

            TextView yes = view1.findViewById(R.id.yes_btn);
            TextView no = view1.findViewById(R.id.no_btn);
            yes.setOnClickListener(l -> {
                //Interface is in JobsOpportunities Fragment
                hasBill(jobId);
                dialog.dismiss();
                mApplyBtn.setEnabled(true);
                navController.popBackStack();
            });

            no.setOnClickListener(l -> {
                dialog.dismiss();
                mApplyBtn.setEnabled(true);
            });
            dialog.show();
        });
    }

    private void applyForJob(String jobId) {
        pDialog.show();
        String url = Urls.APPLY_JOB;
        String userId = String.valueOf(SharedPrefManager.getInstance(context).getUserId());

        AndroidNetworking.post(url)
                .addBodyParameter("student_id", userId)
                .addBodyParameter("job_id", jobId)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //converting response to json object
                            JSONObject obj = response;
                            String message = obj.getString("message");
                            String userFounded = "Saved";
                            //if no error in response
                            if (message.toLowerCase().contains(userFounded.toLowerCase())) {
                                Toast.makeText(context, context.getResources().getString(R.string.apply_job), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.post_job_error), Toast.LENGTH_SHORT).show();
                            }
                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            Log.e("apply", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        pDialog.dismiss();
                        Log.e("applyError", anError.getErrorBody());
                        try {
                            JSONObject error = new JSONObject(anError.getErrorBody());
                            JSONObject data = error.getJSONObject("data");
                            Toast.makeText(context, error.getString("message"), Toast.LENGTH_SHORT).show();
                            if (data.has("student_id")) {
                                Toast.makeText(context, data.getJSONArray("student_id").toString(), Toast.LENGTH_SHORT).show();
                            }
                            if (data.has("job_id")) {
                                Toast.makeText(context, data.getJSONArray("job_id").toString(), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void hasBill(String jobId) {
        pDialog.show();

        String url = Urls.STUDENT_HAS_BILL;

        String studentId = String.valueOf(SharedPrefManager.getInstance(context).getUserId());

        AndroidNetworking.get(url)
                .addQueryParameter("user_id", studentId)
                .addQueryParameter("student_id", studentId)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            pDialog.dismiss();
                            JSONObject jsonObject = response;
                            String message = jsonObject.getString("message");
                            String successMessage = "Saved";
                            if (message.toLowerCase().contains(successMessage.toLowerCase())) {
                                if (!response.getString("data").equals("null")) {

                                    JSONObject data = response.getJSONObject("data");

                                    if (Integer.parseInt(data.getString("is_paid")) == 0) {
                                        SharedPrefManager.getInstance(context).setHasBill(
                                                Integer.parseInt(data.getString("id")),
                                                true,
                                                Integer.parseInt(data.getString("amount"))
                                        );
                                        Log.e(
                                                "bill",
                                                SharedPrefManager.getInstance(context).getBillID() + "  " +
                                                        SharedPrefManager.getInstance(context).getBillAmount() + "  " +
                                                        SharedPrefManager.getInstance(context).hasBill()
                                        );
                                        Toast.makeText(context, context.getResources().getString(R.string.you_have_bill), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(context, context.getResources().getString(R.string.bill_amount) +
                                                        SharedPrefManager.getInstance(context).getBillAmount()
                                                , Toast.LENGTH_SHORT).show();
                                    } else {
                                        SharedPrefManager.getInstance(context).setHasBill(-1, false, -1);
                                        applyForJob(jobId);

                                    }
//                                    }
                                } else {
                                    SharedPrefManager.getInstance(context).setHasBill(-1, false, -1);
                                    applyForJob(jobId);

                                }
                            } else {
                                Toast.makeText(context, context.getResources().getString(R.string.error_load_data), Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();
                            }
                            pDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            Log.e("jobs catch", e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        pDialog.dismiss();
                        Log.e("jobs anerror", error.getErrorBody());
                    }
                });
    }
}