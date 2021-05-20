import axios, {AxiosInstance} from "axios";
import {getToken} from "../useToken";
import React, {Dispatch, useEffect, useState} from "react";
import {Backdrop, CircularProgress} from "@material-ui/core";
import {makeStyles} from '@material-ui/core/styles';
import ErrorDialog from '../components/dialog/ErrorDialog';

const baseURL = "http://localhost:9090";

export const api = axios.create({
    baseURL: baseURL,
    headers: {
        'Content-Type': 'application/json;charset=UTF-8'
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
    const [errorString, setErrorString] = useState("");
    const [errorOpen, setErrorOpen] = useState(false);

    useEffect(function () {
        apiList.forEach((api) => {
            tuneApi(api, setVisibleBackdrop, setErrorString, setErrorOpen);
        });
    }, []);

    const handleErrorClose = () => {
        setErrorOpen(false);
    };

    return (
        <div>
            <ErrorDialog errorString={errorString} open={errorOpen} handleClose={handleErrorClose}/>
            <Backdrop className={classes.backdrop} open={visibleBackdrop} transitionDuration={1000}>
                <CircularProgress color="inherit"/>
            </Backdrop>
        </div>
    );
}

function tuneApi(asiosInst: AxiosInstance, setVisibleBackdrop: Dispatch<boolean>,
                 setErrorString: Dispatch<string>, setErrorOpen: Dispatch<boolean>) {
    asiosInst.interceptors.request.use(function (config) {
        setVisibleBackdrop(true);

        const token = getToken();
        if (token) {
            config.headers.Authorization = `Bearer ${token.token}`
        }

        return config
    }, function (error) {
        setVisibleBackdrop(false);
        return Promise.reject(error);
    });

    asiosInst.interceptors.response.use(function (response) {
        setVisibleBackdrop(false);
        return response.data;
    }, function (error) {
        setVisibleBackdrop(false);

        const response = error.response;
        if (response) {
            if (response.status !== 200) {
                const data: DTO.ExceptionResponseTO = response.data;
                if (data) {
                    if (data.message) {
                        setErrorOpen(true);
                        setErrorString(data.message);
                    }
                }
            }
        }

        return Promise.reject(error);
    });

}
