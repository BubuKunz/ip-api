package yan.zubritskiy.ipapi.ipgeodata.ui.mapper

import yan.zubritskiy.ipapi.ipgeodata.ui.model.IpGeoData
import yan.zubritskiy.ipapi.ipgeodata.repo.model.IpGeoData as RepoModel


class GeoDataMapper {

    fun from(geoData: RepoModel): IpGeoData = IpGeoData(
        query = geoData.query,
        status = geoData.status,
        continent = geoData.continent,
        continentCode = geoData.continentCode,
        country = geoData.country,
        countryCode = geoData.countryCode,
        region = geoData.region,
        regionName = geoData.regionName,
        city = geoData.city,
        district = geoData.district,
        zip = geoData.zip,
        lat = geoData.lat,
        lon = geoData.lon,
        timezone = geoData.timezone,
        offset = geoData.offset,
        currency = geoData.currency,
        isp = geoData.isp,
        org = geoData.org,
        asValue = geoData.asValue,
        asName = geoData.asName,
        reverse = geoData.reverse,
        mobile = geoData.mobile,
        proxy = geoData.proxy,
        hosting = geoData.hosting,
    )
}