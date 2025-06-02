package com.example.lapstore.api

import retrofit2.Call
import retrofit2.http.*

data class Report(
    val id: Int,
    val title: String,
    val description: String?,
    val created_at: String?
)

interface ReportApiService {
    @GET("report_api.php")
    fun getReports(): Call<ReportListResponse>

    @POST("report_api.php")
    fun createReport(@Body report: CreateReportRequest): Call<ApiResponse>

    @PUT("report_api.php")
    fun updateReport(@Body report: UpdateReportRequest): Call<ApiResponse>

    @DELETE("report_api.php")
    fun deleteReport(@Body report: DeleteReportRequest): Call<ApiResponse>
}

data class ReportListResponse(val data: List<Report>)
data class ApiResponse(val message: String?, val error: String?)

data class CreateReportRequest(val title: String, val description: String?)
data class UpdateReportRequest(val id: Int, val title: String, val description: String?)
data class DeleteReportRequest(val id: Int)