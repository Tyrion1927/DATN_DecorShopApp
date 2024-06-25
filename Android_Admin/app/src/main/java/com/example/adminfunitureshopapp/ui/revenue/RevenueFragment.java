package com.example.adminfunitureshopapp.ui.revenue;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adminfunitureshopapp.databinding.FragmentRevenueBinding;
import com.example.adminfunitureshopapp.model.Revenue.Revenue;
import com.example.adminfunitureshopapp.viewmodel.RevenueAPIService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RevenueFragment extends Fragment {

    private FragmentRevenueBinding binding;
    private BarChart barChart;
    private PieChart pieChart;
    private Button productsRevenue;
    private Button monthRevenue;
    private RevenueAPIService revenueAPIService;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRevenueBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        barChart = binding.barchartRevenue;
        pieChart = binding.piechartRevenue;
        productsRevenue = binding.btRevenueProducts;
        monthRevenue = binding.btRevenueMonth;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getData_PieChart();
        productsRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barChart.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
                getData_PieChart();
            }
        });
        monthRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pieChart.setVisibility(View.GONE);
                barChart.setVisibility(View.VISIBLE);
                settings_Barchart();
                getData_BarChart();
            }
        });

    }

    private void getData_PieChart() {
        revenueAPIService = new RevenueAPIService();
        List<PieEntry> revenueData = new ArrayList<>();
        revenueAPIService.getRevenueByProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Revenue>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Revenue> RevenueList) {
                        Log.d("DEBUG", "get Revenue Success");
                        for (Revenue revenue : RevenueList) {
                            Log.d("revenue Total and monnth:", revenue.getTotalMonth() +" " + revenue.getMonth());
                            revenueData.add(new PieEntry(Integer.parseInt(revenue.getTotalProduct()), String.valueOf(revenue.getNameProduct())));
                        }
                        PieDataSet piedataSet = new PieDataSet(revenueData, "Thống kê Sản Phẩm");
                        piedataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        piedataSet.setValueTextSize(12f);

                        PieData pieData = new PieData(piedataSet);
                        pieChart.setData(pieData);
                        pieChart.animateXY(1500,1500);
                        pieChart.getDescription().setEnabled(false);
                        pieChart.setUsePercentValues(true);
                        pieChart.invalidate();
                    }
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG", "get revenue Fail : " + e.getMessage());
                    }
                });
    }

    private void settings_Barchart() {
        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setGranularity(1f); // Khoảng cách giữa các nhãn
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Vị trí nhãn trục x
        barChart.getAxisRight().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1500);
    }

    private void getData_BarChart() {
        revenueAPIService = new RevenueAPIService();
        //List<Revenue> revenueList = new ArrayList<>();
        List<BarEntry> revenueData = new ArrayList<>();
        revenueAPIService.getRevenue()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Revenue>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Revenue> RevenueList) {
                        Log.d("DEBUG", "get Revenue Success");
                        for (Revenue revenue : RevenueList) {
                            Log.d("revenue Total and monnth:", revenue.getTotalMonth() +" " + revenue.getMonth());
                            revenueData.add(new BarEntry(Integer.parseInt(revenue.getMonth())-1, Float.parseFloat(revenue.getTotalMonth())));
                        }
                        BarDataSet bardataSet = new BarDataSet(revenueData, "Thống kê hàng tháng");
                        bardataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        bardataSet.setValueTextSize(12f);

                        BarData barData = new BarData(bardataSet);
                        barChart.setData(barData);
                        barChart.invalidate();
                    }
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG", "get revenue Fail : " + e.getMessage());
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
