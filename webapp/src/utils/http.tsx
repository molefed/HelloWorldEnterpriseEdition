import axios, {AxiosInstance} from "axios";
import {getToken} from "../useToken";
import React, {Dispatch, useEffect, useState} from "react";
import {Backdrop, CircularProgress} from "@material-ui/core";
import { makeStyles } from '@material-ui/core/styles';

const baseURL = "http://localhost:9090";

export const api = axios.create({
    baseURL: baseURL,
    headers: {
        "Content-type": "application/json"
    },
    responseType: "json",
});

const apiList = [api];

const useStyles = makeStyles((theme) => ({
    backdrop: {
        zIndex: theme.zIndex.drawer + 1,
        color: theme.palette.info.light,
    },
}));

export function LoadingOverlay() {
    const classes = useStyles();
    const [visibleBackdrop, setVisibleBackdrop] = useState(false);

    useEffect(function () {
        apiList.forEach((api) => {
            tuneApi(api, setVisibleBackdrop);
        });
    }, []);

    return (
        <Backdrop className={classes.backdrop} open={visibleBackdrop} transitionDuration={1000}>
            <CircularProgress color="inherit"/>
        </Backdrop>
    );
}

function tuneApi(asiosInst: AxiosInstance, setVisibleBackdrop: Dispatch<boolean>) {
    asiosInst.interceptors.request.use(function (config) {
        setVisibleBackdrop(true);

        const token = getToken();
        if (token) {
            config.headers.Authorization = `Bearer ${token}`
        }

        return config
    }, function (error) {
        return Promise.reject(error);
    });

    asiosInst.interceptors.response.use(function (response) {
        setVisibleBackdrop(false);
        return response;
    }, function (error) {
        return Promise.reject(error);
    });
}
