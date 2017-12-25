/**
 *
 */
package com.core.elastic.jobs;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.core.elastic.converters.ModelToDTOConverter;
import com.core.elastic.data.ElasticAccommodationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.tui.group.core.constants.FlexiQueries;
import com.tui.group.core.model.AccommodationModel;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * @author msin82
 * 
 */
public class ExportDataToElasticJob extends AbstractJobPerformable<CronJobModel>
{

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportDataToElasticJob.class);

	@Autowired
	private ModelToDTOConverter modelToDTOConverter;

	@Override
	public PerformResult perform(final CronJobModel crnjob)
	{
		final FlexibleSearchQuery query = new FlexibleSearchQuery(FlexiQueries.VALID_ACCOMMODATIONS_FOR_EXPORT);

		final List<AccommodationModel> products = flexibleSearchService.<AccommodationModel> search(query).getResult();

		final List<ElasticAccommodationDTO> convertedData = modelToDTOConverter.convert(products);
		//createNonIndexMapping();
		try
		{
			System.out.println("<<<<<<< ----------------" + convertedData.size() + "-------------------- >>>>>>");
			final String json = new ObjectMapper().writeValueAsString(convertedData);
			final StringBuilder jsonData = new StringBuilder(json.substring(0, json.length() - 1));
			final Pattern pattern = Pattern.compile("(},)");
			final Matcher matcher = pattern.matcher(jsonData);
			final StringBuilder indexDocs = new StringBuilder(matcher.replaceAll("}\n" + "{\"index\":{}}\n"));
			indexDocs.replace(0, 1, "{\"index\":{}}\n");
			// Below line is important , otherwise the last record wouldn't be pushed
			indexDocs.append("\n");
			final DefaultHttpClient httpClient = new DefaultHttpClient();
			final HttpPut request = new HttpPut("http://localhost:9200/sebluesv/accommodation/_bulk");
			final StringEntity data = new StringEntity(indexDocs.toString(), ContentType.create(
					ContentType.APPLICATION_JSON.getMimeType(), "UTF-8"));
			request.setEntity(data);
			final HttpResponse response = httpClient.execute(request);
			System.out.println(response);
		}
		catch (final IOException error)
		{
			LOGGER.error(" <------------ IOException Occured while pushing data to elastic -----------> ");
			return new PerformResult(CronJobResult.FAILURE, CronJobStatus.ABORTED);
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

	/**
	 * 
	 */
	private void createNonIndexMapping()
	{
		//delete all indexes
		URL url = null;
		try
		{
			url = new URL("http://localhost:9200/sebluesv");
		}
		catch (final MalformedURLException exception)
		{
			exception.printStackTrace();
		}
		deleteIndex(url);

		//create sebluesv index
		createIndex(url);

		//create Mappings
		disableIndexing();

	}

	/**
	 * 
	 */
	private void disableIndexing()
	{
		System.out.println("disable mapping");
		String jsonStrng="{\"properties\":{\"brand\":{\"type\":\"String\",\"index\":\"not_analyzed\"},\"baseProductCode\":{\"type\":\"String\",\"index\":\"not_analyzed\" },\"countryCode\":{\"type\":\"String\",\"index\":\"not_analyzed\"},\"destinationCode\":{\"type\":\"String\",\"index\":\"not_analyzed\"},\"resortCode\":{\"type\":\"String\", \"index\":\"not_analyzed\" },\"conceptCode\":{\"type\":\"String\",\"index\":\"not_analyzed\" } }}";
		URL url = null;
		try
		{
			url = new URL("http://localhost:9200/sebluesv/_mappings/accommodation");
		}
		catch (final MalformedURLException exception)
		{
			exception.printStackTrace();
		}
		HttpURLConnection httpURLConnection = null;
		try
		{
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpURLConnection.setRequestMethod("PUT");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
			dataOutputStream.writeBytes(jsonStrng);
			System.out.println(httpURLConnection.getResponseCode());
		}
		catch (final IOException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			if (httpURLConnection != null)
			{
				httpURLConnection.disconnect();
			}
		}

	}

	/**
	 * @param url
	 */
	private void createIndex(final URL url)
	{System.out.println("creating indexes");

		HttpURLConnection httpURLConnection = null;
		try
		{
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpURLConnection.setRequestMethod("PUT");
			System.out.println(httpURLConnection.getResponseCode());
		}
		catch (final IOException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			if (httpURLConnection != null)
			{
				httpURLConnection.disconnect();
			}
		}


	}

	/**
	 * @param url
	 * @return
	 */
	private void deleteIndex(final URL url)
	{
		System.out.println("deleting indexes");
		HttpURLConnection httpURLConnection = null;
		try
		{
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpURLConnection.setRequestMethod("DELETE");
			System.out.println(httpURLConnection.getResponseCode());
		}
		catch (final IOException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			if (httpURLConnection != null)
			{
				httpURLConnection.disconnect();
			}
		}
	}
}
