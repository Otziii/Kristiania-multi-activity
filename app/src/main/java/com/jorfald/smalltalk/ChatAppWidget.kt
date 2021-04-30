package com.jorfald.smalltalk

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.jorfald.smalltalk.database.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatAppWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        print("")
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        print("")
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    CoroutineScope(Dispatchers.IO).launch {
        val lastMessageObject = AppDatabase.getDatabase(context).chatDAO().getLastMessage()

        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.chat_app_widget)

        views.setTextViewText(R.id.appwidget_text, "\"${lastMessageObject.message}\"")
        views.setTextViewText(R.id.appwidget_user_text, "- ${lastMessageObject.userName}")

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}