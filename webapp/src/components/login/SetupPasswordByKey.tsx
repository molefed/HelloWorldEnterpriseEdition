import * as React from 'react';
import {useState} from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import {makeStyles, Theme} from "@material-ui/core/styles";
import {Copyright} from "./Copyright";
import * as authService from "../../service/AuthService";
import * as userService from "../../service/UserService";

const useStyles = makeStyles((theme: Theme) => ({
    topBox: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        background: theme.palette.secondary.main,
    },
    formBox: {
        width: '100%', // Fix IE11 issue.
        marginTop: theme.spacing(3),
    },
    buttonSubmit: {
        marginTop: theme.spacing(3),
        marginBottom: theme.spacing(2),
        width: '100%'
    }

}));

export default function SetupPasswordByKey() {
    const classes = useStyles();

    const [password, setPassword] = useState("");
    const [passwordAgain, setPasswordAgain] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (password !== passwordAgain) {
            console.log("again not equals")
        } else {
            const query = new URLSearchParams(window.location.href);
            const key = query.get('key');
            if (key === null) {
                console.log("key not found in url")
            } else {
                let user = await userService.setupPasswordByKey(key, password);
                await authService.login(user.name, password);
            }
        }
    }

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <Box className={classes.topBox}>
                <Avatar className={classes.avatar}>
                    <LockOutlinedIcon/>
                </Avatar>
                <Typography component="h1" variant="h5">
                    Sign up
                </Typography>
                <Box
                    component="form"
                    className={classes.formBox}
                    onSubmit={handleSubmit}
                >
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                name="password"
                                label="Password"
                                type="password"
                                id="password"
                                onChange={e => setPassword(e.target.value)}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                name="passwordAgain"
                                label="Password again"
                                type="passwordAgain"
                                id="passwordAgain"
                                onChange={e => setPasswordAgain(e.target.value)}
                            />
                        </Grid>
                    </Grid>
                    <Button type="submit" variant="contained" className={classes.buttonSubmit}>
                        Sign Up
                    </Button>
                    <Grid container /*justifyContent="flex-end"*/>
                        <Grid item>
                            <Link href="/signin" variant="body2">
                                Already have an account? Sign in
                            </Link>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
            <Copyright sx={{mt: 5}}/>
        </Container>
    );
}
